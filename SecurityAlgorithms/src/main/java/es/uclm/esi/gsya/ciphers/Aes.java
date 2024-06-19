package es.uclm.esi.gsya.ciphers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Eugenio
 */

/*
 * Modos y Padding soportados por AES (Advanced Encryption Standard):
 *
 * Modes:
 * ECB
 *  - NoPadding
 *  - PKCS5Padding
 *  - PKCS7Padding
 *  - ISO10126Padding
 * CBC
 *  - NoPadding
 *  - PKCS5Padding
 *  - PKCS7Padding
 *  - ISO10126Padding
 * CFB
 *  - NoPadding
 * OFB
 *  - NoPadding
 * CTR
 *  - NoPadding
 * GCM (Galois/Counter Mode)
 *  - NoPadding
 *
 * Notas:
 * - ECB: Menos seguro debido a la igualdad de cifrados para bloques idénticos de texto plano.
 * - CBC: Adecuado para la mayoría de las aplicaciones que requieren seguridad mejorada respecto a ECB.
 * - CFB, OFB, CTR: Modos que permiten operar sobre flujos de datos y no requieren padding.
 * - GCM: Proporciona cifrado autenticado con eficiencia y es ampliamente utilizado en protocolos de red.
 */

public class Aes {
    private byte[] key;
    private String instanceString = "AES/";
    private byte[] iv;
    
    public Aes(String mode, String padding, int keySize){
        key = generateKey(keySize);
        instanceString += mode + "/" + padding;
        if (mode.equals("GCM")){
            iv = generateIv(12);
        } else if (!mode.equals("ECB")) {
            iv = generateIv(16);
        }
    }
    
    private static byte[] generateKey(int keySize) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave AES", e);
        }
    }
    
    private static byte[] generateIv(int size) {
        byte[] iv = new byte[size]; // 12 bytes (96 bits) para GCM, 16 bytes (128 bits) para otros modos
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }
    
    public void encryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(instanceString);
            if (instanceString.contains("GCM")) {
                GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); // 128 bits de tamaño del tag
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmSpec);
            } else if (!instanceString.contains("ECB")) {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            }

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
            byte[] outputBytes = cipher.doFinal(inputBytes);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                // Escribir IV primero si no es ECB
                if (!instanceString.contains("ECB")) {
                    outputStream.write(iv);
                }
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception("Error al encriptar el archivo", e);
        }
    }
    
    public void decryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance(instanceString);

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());

            if (!instanceString.contains("ECB")) {
                // Extraer IV del archivo cifrado
                System.arraycopy(inputBytes, 0, iv, 0, iv.length);
                byte[] actualCipherText = new byte[inputBytes.length - iv.length];
                System.arraycopy(inputBytes, iv.length, actualCipherText, 0, actualCipherText.length);
                inputBytes = actualCipherText;
            }

            if (instanceString.contains("GCM")) {
                GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv); // 128 bits de tamaño del tag
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmSpec);
            } else if (!instanceString.contains("ECB")) {
                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            }

            byte[] outputBytes = cipher.doFinal(inputBytes);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception("Error al desencriptar el archivo", e);
        }
    }
}

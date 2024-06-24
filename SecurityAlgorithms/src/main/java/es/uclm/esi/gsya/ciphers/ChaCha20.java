package es.uclm.esi.gsya.ciphers;

import es.uclm.esi.gsya.utils.FileHandler;
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
import javax.crypto.spec.ChaCha20ParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Clase para cifrado y descifrado usando ChaCha20 y ChaCha20-Poly1305.
 */
public class ChaCha20 {
    private byte[] key;
    private byte[] nonce;
    private String instanceString;
    private String keyFileName;

    // Bloque estático para registrar Bouncy Castle
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    // Constructor cuando se proporciona un modo y la clave desde un archivo
    public ChaCha20(String mode, String keyPath) throws Exception {
        try {
            key = FileHandler.readKeyFromFile(keyPath);
            setupMode(mode);
        } catch (IOException ex) {
            throw new Exception("Error al leer la clave desde el archivo", ex);
        }
    }

    // Constructor para generar una nueva clave y guardarla en un archivo
    public ChaCha20() {
        key = generateKey();
        try {
            keyFileName = "ChaCha20.key";
            FileHandler.saveKeyToFile(keyFileName, key);
            setupMode("ChaCha20"); // Valor por defecto
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Configuración del modo de cifrado
    private void setupMode(String mode) {
        switch (mode) {
            case "ChaCha20-Poly1305" -> {
                this.instanceString = "ChaCha20-Poly1305";
                this.nonce = generateNonce(12); // 12 bytes para ChaCha20-Poly1305
            }
        }
    }

    // Generación de una nueva clave ChaCha20 de 256 bits
    private static byte[] generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20");
            keyGen.init(256); // ChaCha20 utiliza claves de 256 bits
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave ChaCha20", e);
        }
    }

    // Generación de un nonce de tamaño especificado
    private static byte[] generateNonce(int size) {
        byte[] nonce = new byte[size];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        return nonce;
    }

    // Método para cifrar un archivo
    public void encryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "ChaCha20");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");

            if (instanceString.equals("ChaCha20-Poly1305")) {
                GCMParameterSpec paramSpec = new GCMParameterSpec(128, nonce);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
            }

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
            byte[] outputBytes = cipher.doFinal(inputBytes);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                // Escribir el nonce al principio del archivo
                outputStream.write(nonce);
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException |
                 NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                 NoSuchPaddingException e) {
            throw new Exception("Error al encriptar el archivo", e);
        }
    }

    // Método para descifrar un archivo
    public void decryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "ChaCha20");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());

            // Extraer el nonce del archivo cifrado
            byte[] nonceFromFile = new byte[nonce.length];
            System.arraycopy(inputBytes, 0, nonceFromFile, 0, nonceFromFile.length);
            byte[] actualCipherText = new byte[inputBytes.length - nonceFromFile.length];
            System.arraycopy(inputBytes, nonceFromFile.length, actualCipherText, 0, actualCipherText.length);

            if (instanceString.equals("ChaCha20-Poly1305")) {
                GCMParameterSpec paramSpec = new GCMParameterSpec(128, nonceFromFile);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
            }

            byte[] outputBytes = cipher.doFinal(actualCipherText);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException |
                 NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException |
                 NoSuchPaddingException e) {
            throw new Exception("Error al desencriptar el archivo", e);
        }
    }

    public String getKeyFileName() {
        return keyFileName;
    }
}

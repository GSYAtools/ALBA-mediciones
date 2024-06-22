/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author Eugenio
 */
public class ChaCha20 {
    private byte[] key;
    private byte[] nonce;
    private String instanceString;
    
    public ChaCha20(String mode, String keyPath) throws Exception {
        try {
            key = FileHandler.readKeyFromFile(keyPath);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new Exception("Error al leer la clave desde el archivo", ex);
        }
        Security.addProvider(new BouncyCastleProvider());
        setupMode(mode);
    }
    
    public ChaCha20() {
        key = generateKey();
        try {
            FileHandler.saveKeyToFile("ChaCha20.key", key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void setupMode(String mode) {
        switch (mode) {
            case "ChaCha20-Poly1305":
                this.instanceString = "ChaCha20-Poly1305";
                this.nonce = generateNonce(12); // 12 bytes para ChaCha20-Poly1305
                break;
            case "XChaCha20-Poly1305":
                this.instanceString = "XChaCha20-Poly1305";
                this.nonce = generateNonce(24); // 24 bytes para XChaCha20-Poly1305
                break;
            case "XChaCha20":
                this.instanceString = "XChaCha20";
                this.nonce = generateNonce(24); // 24 bytes para XChaCha20
                break;
            default:
                this.instanceString = "ChaCha20";
                this.nonce = generateNonce(12); // 12 bytes para ChaCha20
                break;
        }
    }
    
    private static byte[] generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20");
            keyGen.init(256);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave ChaCha20", e);
        }
    }
    
    private static byte[] generateNonce(int size) {
        byte[] nonce = new byte[size];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        return nonce;
    }
    
    public void encryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "ChaCha20");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");

            if (instanceString.equals("ChaCha20-Poly1305") || instanceString.equals("XChaCha20-Poly1305")) {
                GCMParameterSpec paramSpec = new GCMParameterSpec(128, nonce);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
            } else {
                ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, paramSpec);
            }

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());
            byte[] outputBytes = cipher.doFinal(inputBytes);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(nonce); // Escribir el nonce al principio del archivo
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception("Error al encriptar el archivo", e);
        }
    }
    
    public void decryptFile(File inputFile, File outputFile) throws Exception {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "ChaCha20");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");

            byte[] inputBytes = Files.readAllBytes(inputFile.toPath());

            // Extraer el nonce del archivo cifrado
            byte[] nonceFromFile = new byte[nonce.length]; // Longitud del nonce usado
            System.arraycopy(inputBytes, 0, nonceFromFile, 0, nonceFromFile.length);
            byte[] actualCipherText = new byte[inputBytes.length - nonceFromFile.length];
            System.arraycopy(inputBytes, nonceFromFile.length, actualCipherText, 0, actualCipherText.length);

            if (instanceString.equals("ChaCha20-Poly1305") || instanceString.equals("XChaCha20-Poly1305")) {
                GCMParameterSpec paramSpec = new GCMParameterSpec(128, nonceFromFile);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
            } else {
                ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonceFromFile, 0);
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, paramSpec);
            }

            byte[] outputBytes = cipher.doFinal(actualCipherText);

            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(outputBytes);
            }
        } catch (IOException | InvalidAlgorithmParameterException | InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            throw new Exception("Error al desencriptar el archivo", e);
        }
    }
}


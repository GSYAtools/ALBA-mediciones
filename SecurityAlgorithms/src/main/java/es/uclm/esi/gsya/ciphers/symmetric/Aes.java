/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.ciphers.symmetric;

import es.uclm.esi.gsya.utils.FileHandler;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Clase que proporciona métodos para cifrar y descifrar archivos utilizando el algoritmo AES en diferentes modos y esquemas de relleno.
 * Permite la generación de claves aleatorias y el manejo de archivos de clave.
 * 
 * <p>El cifrado se puede realizar en modos como ECB y CBC, con soporte para diferentes esquemas de relleno.</p>
 * 
 * <p>Los archivos de clave generados se guardan localmente utilizando la clase FileHandler.</p>
 * 
 * <p>Para el modo GCM, se genera un IV aleatorio de 12 bytes. Para otros modos (como CBC), se utiliza un IV aleatorio de 16 bytes.</p>
 * 
 * <p>Las excepciones durante el cifrado y descifrado son capturadas y relanzadas con mensajes descriptivos.</p>
 * 
 * @author Eugenio
 */
public class Aes {
    private byte[] key;
    private String instanceString = "AES/";
    private byte[] iv;
    private String keyFileName;
    
    /**
     * Constructor que inicializa la instancia de AES con un modo, esquema de relleno y ruta de archivo de clave.
     * Lee la clave desde el archivo especificado.
     * 
     * @param mode Modo de cifrado (ECB, CBC, CFB, OFB, CTR, GCM).
     * @param padding Esquema de relleno (PKCS5Padding, ISO10126Padding, NoPadding).
     * @param keyPath Ruta del archivo de clave.
     */
    public Aes(String mode, String padding, String keyPath){
        try {
            key = FileHandler.readKeyFromFile(keyPath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        instanceString += mode.toUpperCase() + "/" + padding;
        if (mode.equals("GCM")){
            iv = generateIv(12);
        } else if (!mode.equals("ECB")) {
            iv = generateIv(16);
        }
    }
    
    /**
     * Constructor que inicializa la instancia de AES con una longitud de clave especificada.
     * Genera una clave AES de la longitud especificada y guarda la clave en un archivo local.
     * 
     * @param keySize Longitud de la clave AES (128, 192 o 256 bits).
     */
    public Aes(int keySize){
        key = generateKey(keySize);
        try {
            keyFileName = "AES_"+keySize+".key";
            FileHandler.saveKeyToFile(keyFileName, key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Método estático para generar una clave AES de la longitud especificada.
     * 
     * @param keySize Longitud de la clave AES (128, 192 o 256 bits).
     * @return Arreglo de bytes que representa la clave generada.
     */
    private static byte[] generateKey(int keySize) {
        // Verificar que el tamaño de la clave es válido
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new IllegalArgumentException("El tamaño de la clave debe ser 128, 192 o 256 bits.");
        }
        
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave AES", e);
        }
    }
    
    /**
     * Método estático para generar un IV (vector de inicialización) de tamaño especificado.
     * 
     * @param size Tamaño del IV en bytes.
     * @return Arreglo de bytes que representa el IV generado.
     */
    private static byte[] generateIv(int size) {
        byte[] iv = new byte[size]; // 12 bytes (96 bits) para GCM, 16 bytes (128 bits) para otros modos
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return iv;
    }
    
    /**
     * Método para cifrar un archivo de entrada y guardar el resultado en un archivo de salida.
     * 
     * @param inputFile Archivo de entrada a cifrar.
     * @param outputFile Archivo de salida cifrado.
     * @throws Exception Si ocurre un error durante el cifrado.
     */
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
    
    /**
     * Método para descifrar un archivo de entrada y guardar el resultado en un archivo de salida.
     * 
     * @param inputFile Archivo de entrada cifrado.
     * @param outputFile Archivo de salida descifrado.
     * @throws Exception Si ocurre un error durante el descifrado.
     */
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

    /**
     * Retorna el nombre del archivo de clave generado.
     * 
     * @return Nombre del archivo de clave generado.
     */
    public String getKeyFileName() {
        return keyFileName;
    }
}

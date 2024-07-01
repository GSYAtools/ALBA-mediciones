
package es.uclm.esi.gsya.ciphers.symmetric;

import es.uclm.esi.gsya.utils.FileHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.security.Security;
import javax.crypto.spec.SecretKeySpec;


/**
 * Clase que proporciona métodos para cifrar y descifrar archivos utilizando el algoritmo Camellia en diferentes modos y esquemas de relleno.
 * Permite la generación de claves aleatorias y el manejo de archivos de clave.
 * 
 * <p>El cifrado se puede realizar en modos como ECB, CBC, GCM, OCB, entre otros, con soporte para diferentes esquemas de relleno.</p>
 * 
 * <p>Los archivos de clave generados se guardan localmente utilizando la clase FileHandler.</p>
 * 
 * <p>Se utiliza BouncyCastle como proveedor de seguridad para soportar el algoritmo Camellia.</p>
 * 
 * <p>Para el modo GCM y OCB, se genera un IV aleatorio de 12 bytes. Para otros modos (como CBC), se utiliza un IV aleatorio de 16 bytes.</p>
 * 
 * <p>Las excepciones durante el cifrado y descifrado son capturadas y relanzadas con mensajes descriptivos.</p>
 * 
 * @author Eugenio
 */
public class Camellia {
    private byte[] key;
    private String instanceString = "Camellia/";
    private byte[] iv;
    private String keyFileName;
    
    /**
     * Constructor que inicializa la instancia de Camellia con un modo, esquema de relleno y ruta de archivo de clave.
     * Lee la clave desde el archivo especificado y añade BouncyCastle como proveedor de seguridad.
     * 
     * @param mode Modo de cifrado (ECB, CBC, GCM, OCB, etc.).
     * @param padding Esquema de relleno (PKCS5Padding, NoPadding, etc.).
     * @param keyPath Ruta del archivo de clave.
     * @throws IOException Si ocurre un error al leer el archivo de clave.
     */
    public Camellia(String mode, String padding, String keyPath) throws IOException {
        key = FileHandler.readKeyFromFile(keyPath);
        instanceString += mode.toUpperCase() + "/" + padding;

        // Añadir BouncyCastle como proveedor de seguridad
        Security.addProvider(new BouncyCastleProvider());

        if (mode.equals("GCM") || mode.equals("OCB")){
            iv = generateIv(12);
        } else if (!mode.equals("ECB")) {
            iv = generateIv(16);
        }
    }

    /**
     * Constructor que inicializa la instancia de Camellia con una longitud de clave especificada.
     * Genera una clave Camellia de la longitud especificada y guarda la clave en un archivo local.
     * 
     * @param keySize Longitud de la clave Camellia (128, 192 o 256 bits).
     * @throws NoSuchProviderException Si no se encuentra el proveedor de seguridad BouncyCastle.
     * @throws IOException Si ocurre un error al guardar el archivo de clave.
     */
    public Camellia(int keySize) throws NoSuchProviderException, IOException {
        key = generateKey(keySize);
        keyFileName = "Camellia_" + keySize + ".key";
        FileHandler.saveKeyToFile(keyFileName, key);
    }
    
    /**
     * Método estático para generar una clave Camellia de la longitud especificada.
     * 
     * @param keySize Longitud de la clave Camellia (128, 192 o 256 bits).
     * @return Arreglo de bytes que representa la clave generada.
     * @throws NoSuchProviderException Si no se encuentra el proveedor de seguridad BouncyCastle.
     */
    private static byte[] generateKey(int keySize) throws NoSuchProviderException {
        // Verificar que el tamaño de la clave es válido
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new IllegalArgumentException("El tamaño de la clave debe ser 128, 192 o 256 bits.");
        }
        
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            KeyGenerator keyGen = KeyGenerator.getInstance("Camellia", "BC");
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar la clave Camellia", e);
        }
    }
    
    /**
     * Método estático para generar un IV (vector de inicialización) de tamaño especificado.
     * 
     * @param size Tamaño del IV en bytes.
     * @return Arreglo de bytes que representa el IV generado.
     */
    private static byte[] generateIv(int size) {
        byte[] iv = new byte[size]; // 12 bytes (96 bits) para GCM/OCB, 16 bytes (128 bits) para otros modos
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
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "Camellia");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");
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
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, "Camellia");
            Cipher cipher = Cipher.getInstance(instanceString, "BC");

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

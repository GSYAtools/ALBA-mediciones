package es.uclm.esi.gsya.ciphers.symmetric;

import es.uclm.esi.gsya.utils.FileHandler;
import org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.ChaCha20ParameterSpec;
import javax.crypto.spec.GCMParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Esta clase proporciona métodos para la encriptación y desencriptación de archivos
 * utilizando los algoritmos ChaCha20, XChaCha20 y ChaCha20-Poly1305.
 * También incluye métodos para la generación de claves y para el manejo de claves en formato de byte[].
 * <p>
 * Se utiliza Bouncy Castle como proveedor criptográfico para soportar estos algoritmos.
 * La clase permite encriptar y desencriptar archivos a nivel de byte, incluyendo el manejo del nonce.
 * </p>
 */
public class ChaCha20 {   
    private String keyFileName;
    private byte[] key;
    private byte[] nonce;
    private int nonceLength; // Longitud del nonce (12 bytes para ChaCha20, 24 bytes para XChaCha20)
    private String algorithm; // Variable para seleccionar el algoritmo

    /**
     * Constructor de la clase ChaCha20 que permite seleccionar el algoritmo de encriptación.
     *
     * @param algorithm El algoritmo a utilizar: "ChaCha20", "XChaCha20", o "ChaCha20-Poly1305".
     * @throws IllegalArgumentException Si el algoritmo proporcionado no es válido.
     */
    public ChaCha20(String algorithm) {
        Security.addProvider(new BouncyCastleFipsProvider());
        setAlgorithm(algorithm);
        generateKey();
        this.nonce = new byte[nonceLength];
        this.keyFileName = algorithm+".key";
    }
    
    /**
    * Constructor de la clase ChaCha20 que permite seleccionar el algoritmo de encriptación,
    * y establecer la clave desde el archivo especificado.
    *
    * @param algorithm El algoritmo a utilizar: "ChaCha20", "XChaCha20", o "ChaCha20-Poly1305".
    * @param keyFile La ruta del archivo que contiene la clave de encriptación.
    * @throws IOException Si ocurre un error al leer la clave o el nonce desde los archivos especificados.
    * @throws IllegalArgumentException Si el algoritmo proporcionado no es válido, o si la clave o el nonce
    *                                  no tienen la longitud requerida para el algoritmo seleccionado.
    */
    public ChaCha20(String algorithm, String keyFile) throws IOException{
        Security.addProvider(new BouncyCastleFipsProvider());
        setAlgorithm(algorithm);
        this.keyFileName = keyFile;
        setKey(FileHandler.readKeyFromFile(keyFileName));
    }

    /**
     * Establece el algoritmo de encriptación a utilizar.
     *
     * @param algorithm El algoritmo a utilizar: "ChaCha20", "XChaCha20", o "ChaCha20-Poly1305".
     * @throws IllegalArgumentException Si el algoritmo proporcionado no es válido.
     */
    public void setAlgorithm(String algorithm) {
        switch (algorithm) {
            case "ChaCha20":
                this.algorithm = "ChaCha20";
                this.nonceLength = 12;
                break;
            case "XChaCha20":
                this.algorithm = "XChaCha20";
                this.nonceLength = 24;
                break;
            case "ChaCha20-Poly1305":
                this.algorithm = "ChaCha20-Poly1305";
                this.nonceLength = 12; // ChaCha20-Poly1305 utiliza un nonce de 12 bytes
                break;
            default:
                throw new IllegalArgumentException("Algoritmo no soportado: " + algorithm);
        }
    }

    /**
     * Genera una nueva clave de encriptación ChaCha20 o XChaCha20 de 256 bits (32 bytes).
     *
     */
    public void generateKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("ChaCha20", "BCFIPS");
            keyGen.init(256, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            this.key = secretKey.getEncoded();
            FileHandler.saveKeyToFile(keyFileName, key);
        } catch (Exception e) {
            throw new RuntimeException("Error generating key", e);
        }
    }

    /**
     * Obtiene el nombre generado para el fichero clave.
     *
     * @return El nombre del fichero que contiene la clave.
     */
    public String getKeyFileName() {
        return this.keyFileName;
    }

    /**
     * Establece la clave de encriptación para usar en el proceso de encriptación y desencriptación.
     *
     * @param key La clave de encriptación en formato de byte[].
     * @throws IllegalArgumentException Si la clave no tiene 256 bits (32 bytes).
     */
    public void setKey(byte[] key) {
        if (key.length != 32) {
            throw new IllegalArgumentException("Invalid key length. Key must be 256 bits (32 bytes).");
        }
        this.key = key;
    }

    /**
     * Genera un nuevo nonce para el algoritmo seleccionado.
     *
     * @return El nonce generado como un arreglo de bytes.
     */
    public byte[] generateNonce() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(this.nonce);
        return this.nonce;
    }

    /**
     * Obtiene el nonce actual utilizado para la encriptación o desencriptación.
     *
     * @return El nonce actual como un arreglo de bytes.
     */
    public byte[] getNonce() {
        return this.nonce;
    }

    /**
     * Establece el nonce para usar en el proceso de encriptación y desencriptación.
     *
     * @param nonce El nonce en formato de byte[].
     * @throws IllegalArgumentException Si el nonce no tiene la longitud adecuada para el algoritmo seleccionado.
     */
    public void setNonce(byte[] nonce) {
        if (nonce.length != nonceLength) {
            throw new IllegalArgumentException("Invalid nonce length. Nonce must be " + nonceLength + " bytes.");
        }
        this.nonce = nonce;
    }

    /**
     * Encripta un archivo utilizando el algoritmo seleccionado.
     *
     * @param inputFile  La ruta del archivo de entrada que se desea encriptar.
     * @param outputFile La ruta del archivo de salida donde se almacenará el archivo encriptado.
     * @throws Exception Si ocurre un error durante el proceso de encriptación.
     */
    public void encrypt(Path inputFile, Path outputFile) throws Exception {
        // Generar un nuevo nonce para cada encriptación
        this.nonce = generateNonce();
        processFile(Cipher.ENCRYPT_MODE, inputFile, outputFile);
    }

    /**
     * Desencripta un archivo previamente encriptado con el algoritmo seleccionado.
     *
     * @param inputFile  La ruta del archivo encriptado que se desea desencriptar.
     * @param outputFile La ruta del archivo de salida donde se almacenará el archivo desencriptado.
     * @throws Exception Si ocurre un error durante el proceso de desencriptación.
     */
    public void decrypt(Path inputFile, Path outputFile) throws Exception {
        processFile(Cipher.DECRYPT_MODE, inputFile, outputFile);
    }

    /**
     * Procesa el archivo para encriptación o desencriptación dependiendo del modo especificado.
     *
     * @param mode       El modo de operación (Cipher.ENCRYPT_MODE o Cipher.DECRYPT_MODE).
     * @param inputFile  La ruta del archivo de entrada.
     * @param outputFile La ruta del archivo de salida.
     * @throws Exception Si ocurre un error durante el procesamiento del archivo.
     */
    private void processFile(int mode, Path inputFile, Path outputFile) throws Exception {
        Cipher cipher;
        switch (algorithm) {
            case "ChaCha20":
                cipher = Cipher.getInstance("ChaCha20", "BCFIPS");
                cipher.init(mode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha20"), new ChaCha20ParameterSpec(nonce, 0));
                break;
            case "XChaCha20":
                cipher = Cipher.getInstance("XChaCha20", "BCFIPS");
                cipher.init(mode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha20"), new ChaCha20ParameterSpec(nonce, 0));
                break;
            case "ChaCha20-Poly1305":
                cipher = Cipher.getInstance("ChaCha20-Poly1305", "BCFIPS");
                GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce); // 128-bit authentication tag
                cipher.init(mode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha20"), gcmSpec);
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algorithm);
        }

        try (FileInputStream fis = new FileInputStream(inputFile.toFile());
             FileOutputStream fos = new FileOutputStream(outputFile.toFile())) {

            // Para encriptar, escribimos el nonce al principio del archivo encriptado
            if (mode == Cipher.ENCRYPT_MODE) {
                fos.write(nonce);
            } else {
                // Para desencriptar, necesitamos leer el nonce del archivo
                fis.read(nonce);
                if (algorithm.equals("ChaCha20-Poly1305")) {
                    GCMParameterSpec gcmSpec = new GCMParameterSpec(128, nonce);
                    cipher.init(mode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha20"), gcmSpec);
                } else {
                    ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 0);
                    cipher.init(mode, new javax.crypto.spec.SecretKeySpec(key, "ChaCha20"), paramSpec);
                }
            }

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }
        }
    }
}

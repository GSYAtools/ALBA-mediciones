package es.uclm.esi.gsya.ciphers.symmetric;

import static es.uclm.esi.gsya.securityalgorithms.OptionValues.*;
import es.uclm.esi.gsya.utils.FileHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private final String keyFileName = "ChaCha20.key";
    private final String nonceFileName = "ChaCha20.nonce";
    public static final int NONCE_SIZE = 12; // Tamaño estándar para ChaCha20-Poly1305 (otros tamaños 8 y 16)
    private static final int TAG_SIZE_BITS = 128; // Tamaño estándar del TAG para ChaCha20-Poly1305
    private static final int KEY_SIZE = 256;
    private static final int BUFFER_SIZE = 4096; // Tamaño del buffer para leer/escribir archivos
    private SecretKey key;
    private byte[] nonce;
    
    //Constructor para generar clave y nonce
    public ChaCha20(String algorithm, int nonceSize) throws IOException, NoSuchAlgorithmException{
        generateKey(algorithm);
        generateNonce(nonceSize);
    }
    
    //Contructor para encriptar y desencriptar
    public ChaCha20(String keyPath, String noncePath, String algorithm) throws IOException{
        byte[] keyBytes = FileHandler.readKeyFromFile(keyPath);
        key = new SecretKeySpec(keyBytes, algorithm);
        nonce = FileHandler.readKeyFromFile(noncePath);
    }
    // Genera una clave de ChaCha20
    private void generateKey(String algorithm) throws NoSuchAlgorithmException, IOException {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(KEY_SIZE);
        byte[] key = keyGen.generateKey().getEncoded();
        FileHandler.saveKeyToFile(keyFileName, key);
    }

    // Genera un nonce (número aleatorio utilizado una sola vez)
    private void generateNonce(int size) throws IOException {
        byte[] nonce = new byte[size];
        new SecureRandom().nextBytes(nonce);
        FileHandler.saveKeyToFile(nonceFileName, nonce);
    }

    // Encripta datos usando ChaCha20
    public void encryptFileChaCha20(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALG_CHACHA20);
        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

        processFile(cipher, inputFile, outputFile);
    }

    // Desencripta un archivo completo usando ChaCha20
    public void decryptFileChaCha20(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALG_CHACHA20);
        ChaCha20ParameterSpec paramSpec = new ChaCha20ParameterSpec(nonce, 1);
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        processFile(cipher, inputFile, outputFile);
    }

    // Encripta un archivo completo usando ChaCha20-Poly1305
    public void encryptFileChaCha20Poly1305(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALG_CHACHA20_POLY1305);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_SIZE_BITS, nonce);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

        processFile(cipher, inputFile, outputFile);
    }

    // Desencripta un archivo completo usando ChaCha20-Poly1305
    public void decryptFileChaCha20Poly1305(File inputFile, File outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(ALG_CHACHA20_POLY1305);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_SIZE_BITS, nonce);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);

        processFile(cipher, inputFile, outputFile);
    }

    // Procesa el archivo (cifra o descifra) usando el cifrador
    private void processFile(Cipher cipher, File inputFile, File outputFile) throws Exception {
        try (InputStream in = new FileInputStream(inputFile);
             OutputStream out = new FileOutputStream(outputFile)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                byte[] output = cipher.update(buffer, 0, bytesRead);
                if (output != null) {
                    out.write(output);
                }
            }
            byte[] outputBytes = cipher.doFinal();
            if (outputBytes != null) {
                out.write(outputBytes);
            }
        }
    }
    
    public String getKeyFileName() {
        return keyFileName;
    }
    
    public String getNonceFileName() {
        return nonceFileName;
    }
}

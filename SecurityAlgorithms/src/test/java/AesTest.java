import es.uclm.esi.gsya.ciphers.symmetric.Aes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

public class AesTest {

    private static final String TEST_INPUT_FILE = "input.txt";
    private static final String TEST_OUTPUT_FILE = "output.enc";
    private static final String TEST_KEY_FILE = "AES_128.key";

    private Aes aes;

    @BeforeEach
    public void setUp() throws IOException {
        // Crear un archivo de entrada para propósitos de prueba
        File inputFile = new File(TEST_INPUT_FILE);
        Files.write(inputFile.toPath(), "Hello, AES!".getBytes());

        // Generar una clave AES de 128 bits y guardarla en un archivo
        aes = new Aes(128);
    }

    @AfterEach
    public void tearDown() {
        // Eliminar archivos de prueba después de cada test
        File inputFile = new File(TEST_INPUT_FILE);
        File outputFile = new File(TEST_OUTPUT_FILE);
        File keyFile = new File(TEST_KEY_FILE);

        if (inputFile.exists()) {
            inputFile.delete();
        }
        if (outputFile.exists()) {
            outputFile.delete();
        }
        if (keyFile.exists()) {
            keyFile.delete();
        }
    }

    @Test
    public void testGenerateAesKeyAndSaveToFile() {
        String keyFileName = aes.getKeyFileName();
        assertNotNull(keyFileName, "Key file name should not be null.");
        assertTrue(new File(keyFileName).exists(), "Key file should exist.");
    }
    
    @Test
    public void testEncryptAndDecryptFile() {
        File inputFile = new File(TEST_INPUT_FILE);
        File outputFile = new File(TEST_OUTPUT_FILE);
        
        Aes aes = new Aes("ECB", "PKCS5Padding", TEST_KEY_FILE);
        try {
            aes.encryptFile(inputFile, outputFile);
        } catch (Exception e) {
            fail("Failed to encrypt file: " + e.getMessage());
        }

        assertTrue(outputFile.exists(), "Encrypted file should exist.");

        try {
            File decryptedFile = new File("decrypted.txt");
            aes.decryptFile(outputFile, decryptedFile);

            String decryptedContent = new String(Files.readAllBytes(decryptedFile.toPath()));
            assertEquals("Hello, AES!", decryptedContent, "Decrypted content should match original input.");
        } catch (Exception e) {
            fail("Failed to decrypt file: " + e.getMessage());
        }
    }

    @Test
    public void testEncryptFileWithInvalidMode() {
        Aes aesInvalidMode = new Aes("InvalidMode", "PKCS5Padding", TEST_KEY_FILE);
        File inputFile = new File(TEST_INPUT_FILE);
        File outputFile = new File(TEST_OUTPUT_FILE);

        assertThrows(Exception.class, () -> aesInvalidMode.encryptFile(inputFile, outputFile),
                "Expected exception for invalid mode.");
    }

    @Test
    public void testDecryptFileWithInvalidKey() {
        // Crear un archivo con contenido aleatorio como archivo cifrado
        File encryptedFile = new File("encrypted.bin");
        byte[] randomData = new byte[1024];
        new SecureRandom().nextBytes(randomData);
        try {
            Files.write(encryptedFile.toPath(), randomData);
        } catch (IOException e) {
            fail("Failed to create encrypted file: " + e.getMessage());
        }

        // Intentar descifrar con una clave incorrecta
        Aes aesInvalidKey = new Aes(128);
        File decryptedFile = new File("decrypted.bin");

        assertThrows(Exception.class, () -> aesInvalidKey.decryptFile(encryptedFile, decryptedFile),
                "Expected exception for invalid key.");
    }
}
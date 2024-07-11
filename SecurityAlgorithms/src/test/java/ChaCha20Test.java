/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import es.uclm.esi.gsya.utils.FileHandler;
import es.uclm.esi.gsya.ciphers.symmetric.ChaCha20;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

public class ChaCha20Test {

    private static final String TEST_ALGORITHM = "ChaCha20";
    private static final String TEST_KEY_FILE = "ChaCha20.key";
    private static final String TEST_INPUT_FILE = "testInput.txt";
    private static final String TEST_ENCRYPTED_FILE = "testEncrypted.enc";
    private static final String TEST_DECRYPTED_FILE = "testDecrypted.txt";
    private static final String TEST_DATA = "Esto es un texto de prueba para cifrado y descifrado.";

    @BeforeEach
    public void setUp() throws IOException {
        // Crear archivo de prueba
        Files.write(Path.of(TEST_INPUT_FILE), TEST_DATA.getBytes());

        // Limpiar archivos anteriores
        Files.deleteIfExists(Path.of(TEST_KEY_FILE));
        Files.deleteIfExists(Path.of(TEST_ENCRYPTED_FILE));
        Files.deleteIfExists(Path.of(TEST_DECRYPTED_FILE));
    }

    @Test
    public void testKeyGeneration() {
        ChaCha20 cipher = new ChaCha20(TEST_ALGORITHM);
        byte[] key = null;
        try {
            key = FileHandler.readKeyFromFile(cipher.getKeyFileName());
        } catch (IOException ex) {
            Logger.getLogger(ChaCha20Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertNotNull(key);
        assertEquals(32, key.length);
        assertTrue(Files.exists(Path.of(TEST_KEY_FILE)));
    }

    @Test
    public void testEncryptionDecryption() throws Exception {
        ChaCha20 cipher = new ChaCha20(TEST_ALGORITHM);
        cipher.encrypt(Path.of(TEST_INPUT_FILE), Path.of(TEST_ENCRYPTED_FILE));
        assertTrue(Files.exists(Path.of(TEST_ENCRYPTED_FILE)));

        cipher.decrypt(Path.of(TEST_ENCRYPTED_FILE), Path.of(TEST_DECRYPTED_FILE));
        assertTrue(Files.exists(Path.of(TEST_DECRYPTED_FILE)));

        byte[] originalData = Files.readAllBytes(Path.of(TEST_INPUT_FILE));
        byte[] decryptedData = Files.readAllBytes(Path.of(TEST_DECRYPTED_FILE));
        assertArrayEquals(originalData, decryptedData);
    }

    @Test
    public void testNonceGeneration() {
        ChaCha20 cipher = new ChaCha20(TEST_ALGORITHM);
        byte[] nonce1 = cipher.generateNonce();
        byte[] nonce2 = cipher.generateNonce();
        assertNotNull(nonce1);
        assertNotNull(nonce2);
        assertEquals(12, nonce1.length);
        assertEquals(12, nonce2.length);
        assertFalse(Arrays.equals(nonce1, nonce2));
    }

    @Test
    public void testSetKeyAndNonce() {
        ChaCha20 cipher = new ChaCha20(TEST_ALGORITHM);
        byte[] newKey = new byte[32];
        new SecureRandom().nextBytes(newKey);
        cipher.setKey(newKey);
        assertArrayEquals(newKey, cipher.getKeyFileName().getBytes());

        byte[] newNonce = new byte[12];
        new SecureRandom().nextBytes(newNonce);
        cipher.setNonce(newNonce);
        assertArrayEquals(newNonce, cipher.getNonce());
    }
}


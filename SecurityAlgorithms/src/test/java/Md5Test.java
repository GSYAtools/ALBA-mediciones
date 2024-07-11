import es.uclm.esi.gsya.hashes.Md5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class Md5Test {

    private Md5 md5;
    private File testFile;

    @BeforeEach
    public void setUp() throws IOException {
        md5 = new Md5();
        testFile = new File("testfile.txt");
        // Crear un archivo de prueba con contenido para los propósitos del test
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write("Hello, World!");
        }
    }

    @AfterEach
    public void tearDown() {
        // Eliminar el archivo de prueba después de cada test
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testGenerateMd5() {
        String expectedHash = "65a8e27d8879283831b664bd8b7f0ad4";
        String generatedHash = md5.generateMd5(testFile);
        assertEquals(expectedHash, generatedHash.toLowerCase(), "Generated MD5 hash does not match expected.");
    }

    @Test
    public void testVerifyMd5WithCorrectHash() {
        String expectedHash = "65a8e27d8879283831b664bd8b7f0ad4";
        boolean result = md5.verifyMd5(testFile, expectedHash);
        assertTrue(result, "Expected MD5 hash does not match generated hash.");
    }

    @Test
    public void testVerifyMd5WithIncorrectHash() {
        String incorrectHash = "incorrecthash";
        boolean result = md5.verifyMd5(testFile, incorrectHash);
        assertFalse(result, "Expected MD5 hash should not match incorrect hash.");
    }

    @Test
    public void testGenerateMd5ForNonExistentFile() {
        File nonExistentFile = new File("nonexistentfile.txt");
        assertThrows(RuntimeException.class, () -> md5.generateMd5(nonExistentFile), "Expected RuntimeException for non-existent file.");
    }
}
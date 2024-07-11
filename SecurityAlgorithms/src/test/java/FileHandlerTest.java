import es.uclm.esi.gsya.utils.FileHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private static final String TEST_FILE_PATH = "test.txt";
    private static final String TEST_BIN_FILE_PATH = "test.key";
    private static final byte[] TEST_KEY_BYTES = {0x01, 0x02, 0x03};
    private static final String TEST_TEXT = "Hello, World!";

    @BeforeEach
    public void setUp() throws IOException {
        Files.createFile(Paths.get(TEST_FILE_PATH));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
    }

    @Test
    public void testSaveAndReadKeyFromFile() {
        try {
            FileHandler.saveKeyToFile(TEST_BIN_FILE_PATH, TEST_KEY_BYTES);
            byte[] readBytes = FileHandler.readKeyFromFile(TEST_BIN_FILE_PATH);
            assertArrayEquals(TEST_KEY_BYTES, readBytes);
        } catch (IOException e) {
            System.out.println("IOException should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSaveAndReadTextFromFile() {
        try {
            FileHandler.saveTextToFile(TEST_FILE_PATH, TEST_TEXT);
            String readText = FileHandler.readTextFromFile(TEST_FILE_PATH);
            assertEquals(TEST_TEXT, readText);
        } catch (IOException e) {
            System.out.println("IOException should not be thrown: " + e.getMessage());
        }
    }
}

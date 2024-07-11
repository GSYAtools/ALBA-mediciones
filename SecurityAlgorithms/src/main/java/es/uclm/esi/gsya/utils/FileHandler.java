package es.uclm.esi.gsya.utils;

import java.io.*;

/**
 * Utility class for handling file operations such as saving and reading data.
 * Supports saving byte arrays and text to files, as well as reading byte arrays
 * and text from files.
 *
 * @author Eugenio
 */
public class FileHandler {

    /**
     * Saves a byte array to a file.
     *
     * @param filePath The path to the file where the byte array will be saved.
     * @param keyBytes The byte array to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveKeyToFile(String filePath, byte[] keyBytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(keyBytes);
        } catch (IOException e) {
            throw new IOException("Error saving key to file: " + filePath, e);
        }
    }

    /**
     * Reads a byte array from a file.
     *
     * @param filePath The path to the file from which the byte array will be read.
     * @return The byte array read from the file.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    public static byte[] readKeyFromFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            return keyBytes;
        } catch (IOException e) {
            throw new IOException("Error reading key from file: " + filePath, e);
        }
    }

    /**
     * Saves text to a file using UTF-8 encoding.
     *
     * @param filePath The path to the file where the text will be saved.
     * @param text The text to be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void saveTextToFile(String filePath, String text) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {
            writer.write(text);
        } catch (IOException e) {
            throw new IOException("Error saving text to file: " + filePath, e);
        }
    }

    /**
     * Reads text from a file using UTF-8 encoding.
     *
     * @param filePath The path to the file from which the text will be read.
     * @return The text read from the file.
     * @throws IOException If an I/O error occurs while reading from the file.
     */
    public static String readTextFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            char[] buffer = new char[1024];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(buffer, 0, buffer.length)) != -1) {
                content.append(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new IOException("Error reading text from file: " + filePath, e);
        }
        return content.toString();
    }
}

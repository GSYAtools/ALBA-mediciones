
package es.uclm.esi.gsya.hashes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase que proporciona métodos para calcular y verificar el hash MD5 de archivos.
 * Utiliza la clase MessageDigest de Java para calcular el hash MD5.
 * 
 * @author Eugenio
 */
public class Md5 {
    private MessageDigest md;

    /**
     * Constructor que inicializa el objeto MessageDigest para el algoritmo MD5.
     * Lanza una excepción RuntimeException si el algoritmo MD5 no está disponible.
     */
    public Md5() {
        try {
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 Algorithm not available", e);
        }
    }

    /**
     * Genera el resumen MD5 de un archivo dado.
     *
     * @param file El archivo para el cual se calculará el hash MD5.
     * @return El hash MD5 del archivo como una cadena hexadecimal.
     * @throws RuntimeException Si ocurre un error al leer el archivo.
     */
    public String generateMd5(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            md.reset();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                md.update(buffer, 0, bytesRead);
            }
            byte[] messageDigest = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    /**
     * Verifica si el hash MD5 generado de un archivo coincide con el hash MD5 proporcionado.
     *
     * @param file El archivo para el cual se verificará el hash MD5.
     * @param md5Hash El hash MD5 esperado en formato hexadecimal.
     * @return True si el hash generado coincide con el hash proporcionado, False en caso contrario.
     */
    public boolean verifyMd5(File file, String md5Hash) {
        String generatedMd5 = generateMd5(file);
        return generatedMd5.equalsIgnoreCase(md5Hash);
    }
}

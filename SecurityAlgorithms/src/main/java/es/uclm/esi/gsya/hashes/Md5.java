package es.uclm.esi.gsya.hashes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author alarcos
 */
public class Md5 {
    private MessageDigest md;

    // Constructor para inicializar el MessageDigest para MD5
    public Md5() {
        try {
            // Crear una instancia de MessageDigest para MD5
            this.md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            // Manejar la excepción si el algoritmo MD5 no está disponible
            throw new RuntimeException("MD5 Algorithm not available", e);
        }
    }

    // Método para generar el resumen MD5 de un archivo
    public String generateMd5(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            // Reiniciar el MessageDigest
            md.reset();

            // Leer el archivo en bloques de 1024 bytes
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                // Actualizar el digest con los bytes leídos
                md.update(buffer, 0, bytesRead);
            }

            // Obtener el hash en bytes
            byte[] messageDigest = md.digest();

            // Convertir los bytes en formato hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                // Convertir cada byte en una cadena hexadecimal de dos dígitos
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (IOException e) {
            // Manejar la excepción si hay un error al leer el archivo
            throw new RuntimeException("Error reading file", e);
        }
    }

    // Método para verificar si el resumen MD5 de un archivo coincide con un hash dado
    public boolean verifyMd5(File file, String md5Hash) {
        // Generar el resumen MD5 del archivo
        String generatedMd5 = generateMd5(file);
        // Comparar el resumen generado con el resumen proporcionado
        return generatedMd5.equalsIgnoreCase(md5Hash);
    }
}

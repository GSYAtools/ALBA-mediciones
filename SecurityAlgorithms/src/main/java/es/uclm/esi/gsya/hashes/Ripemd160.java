package es.uclm.esi.gsya.hashes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.bouncycastle.crypto.digests.RIPEMD160Digest;

/**
 * Clase que proporciona métodos para generar y verificar el hash RIPEMD-160 de archivos.
 * Utiliza la implementación de RIPEMD-160 del proveedor Bouncy Castle.
 * 
 * @author Eugenio
 */
public class Ripemd160 {
    /**
     * Genera el hash RIPEMD-160 de un archivo.
     * 
     * @param filePath La ruta del archivo.
     * @return El hash RIPEMD-160 como una cadena hexadecimal.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public String resume(String filePath) throws IOException {
        byte[] hash = computeRIPEMD160Hash(filePath);
        return bytesToHex(hash);
    }

    /**
     * Verifica si el hash proporcionado coincide con el hash RIPEMD-160 del archivo.
     * 
     * @param filePath La ruta del archivo.
     * @param expectedHash El hash esperado en formato hexadecimal.
     * @return True si el hash coincide, False en caso contrario.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public boolean verify(String filePath, String expectedHash) throws IOException {
        String actualHash = resume(filePath);
        return actualHash.equals(expectedHash);
    }

    /**
    * Calcula el hash RIPEMD-160 de un archivo especificado.
    *
    * @param filePath La ruta del archivo para calcular el hash.
    * @return El hash RIPEMD-160 del archivo como un arreglo de bytes.
    * @throws IOException Si ocurre un error al leer el archivo.
    */
    private byte[] computeRIPEMD160Hash(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            RIPEMD160Digest digest = new RIPEMD160Digest();
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Lee el archivo en bloques y actualiza el digest
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            // Obtiene el resultado final del hash
            byte[] hash = new byte[digest.getDigestSize()];
            digest.doFinal(hash, 0);
            return hash;
        }
    }

    /**
    * Convierte un arreglo de bytes en una cadena hexadecimal.
    *
    * @param bytes El arreglo de bytes a convertir.
    * @return La representación en formato hexadecimal de los bytes.
    */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

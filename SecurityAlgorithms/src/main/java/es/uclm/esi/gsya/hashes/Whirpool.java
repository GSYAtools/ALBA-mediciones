/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.hashes;

import org.bouncycastle.crypto.digests.WhirlpoolDigest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author alarcos
 */
public class Whirpool {
    /**
     * Genera el hash WHIRLPOOL de un archivo.
     * 
     * @param filePath La ruta del archivo.
     * @return El hash WHIRLPOOL como una cadena hexadecimal.
     * @throws IOException Si ocurre un error de lectura del archivo.
     */
    public String resume(String filePath) throws IOException {
        byte[] hash = computeWhirlpoolHash(filePath);
        return bytesToHex(hash);
    }

    /**
     * Verifica si el hash proporcionado coincide con el hash WHIRLPOOL del archivo.
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

    // Método para calcular el hash WHIRLPOOL de un archivo
    private byte[] computeWhirlpoolHash(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            WhirlpoolDigest digest = new WhirlpoolDigest();
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

    // Método auxiliar para convertir bytes a una cadena hexadecimal
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}

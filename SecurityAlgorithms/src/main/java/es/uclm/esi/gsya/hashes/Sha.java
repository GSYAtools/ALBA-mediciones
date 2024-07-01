
package es.uclm.esi.gsya.hashes;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jcajce.provider.digest.SHA3;

/**
 * Clase que proporciona métodos para calcular y verificar hashes de archivos utilizando diferentes algoritmos de hash,
 * incluyendo SHA-1, SHA-256, SHA-512 y SHA-3 (256 y 512 bits).
 * Utiliza las implementaciones de Bouncy Castle para SHA-3.
 * 
 * @author Eugenio
 */
public class Sha {

    /**
     * Calcula el hash SHA-1 de un archivo especificado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @return El hash SHA-1 del archivo como una cadena hexadecimal.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-1.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public String resumeSHA1(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-1");
    }

    /**
     * Calcula el hash SHA-256 de un archivo especificado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @return El hash SHA-256 del archivo como una cadena hexadecimal.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-256.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public String resumeSHA256(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-256");
    }

    /**
     * Calcula el hash SHA-512 de un archivo especificado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @return El hash SHA-512 del archivo como una cadena hexadecimal.
     * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-512.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public String resumeSHA512(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-512");
    }

    /**
     * Calcula el hash SHA-3 (256 bits) de un archivo especificado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @return El hash SHA-3 (256 bits) del archivo como una cadena hexadecimal.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public String resumeSHA3_256(String filePath) throws IOException {
        return hashFileSHA3(filePath, 256);
    }

    /**
     * Calcula el hash SHA-3 (512 bits) de un archivo especificado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @return El hash SHA-3 (512 bits) del archivo como una cadena hexadecimal.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public String resumeSHA3_512(String filePath) throws IOException {
        return hashFileSHA3(filePath, 512);
    }

    /**
     * Método genérico para calcular el hash de un archivo usando un algoritmo de hash dado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @param algorithm El nombre del algoritmo de hash (por ejemplo, "SHA-256").
     * @return El hash calculado del archivo como una cadena hexadecimal.
     * @throws NoSuchAlgorithmException Si el algoritmo especificado no está disponible.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private String hashFile(String filePath, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return hashFileWithDigest(filePath, digest);
    }

    /**
     * Método específico para calcular el hash SHA-3 de un archivo.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @param bitLength La longitud del hash SHA-3 (256 o 512 bits).
     * @return El hash SHA-3 del archivo como una cadena hexadecimal.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private String hashFileSHA3(String filePath, int bitLength) throws IOException {
        SHA3.DigestSHA3 digest;
        switch (bitLength) {
            case 256:
                digest = new SHA3.Digest256();
                break;
            case 512:
                digest = new SHA3.Digest512();
                break;
            default:
                throw new IllegalArgumentException("Unsupported SHA-3 bit length: " + bitLength);
        }
        return hashFileWithDigest(filePath, digest);
    }

    /**
     * Método genérico para calcular el hash de un archivo usando un objeto MessageDigest dado.
     *
     * @param filePath La ruta del archivo para calcular el hash.
     * @param digest El objeto MessageDigest configurado para el algoritmo de hash deseado.
     * @return El hash calculado del archivo como una cadena hexadecimal.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    private String hashFileWithDigest(String filePath, MessageDigest digest) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        }
        byte[] hashBytes = digest.digest();
        return bytesToHex(hashBytes);
    }

    /**
     * Verifica si el hash calculado de un archivo coincide con el hash esperado.
     *
     * @param filePath La ruta del archivo para verificar el hash.
     * @param expectedHash El hash esperado en formato hexadecimal.
     * @param algorithm El nombre del algoritmo de hash (por ejemplo, "SHA-256").
     * @return True si el hash calculado coincide con el hash esperado, False en caso contrario.
     * @throws NoSuchAlgorithmException Si el algoritmo especificado no está disponible.
     * @throws IOException Si ocurre un error al leer el archivo.
     */
    public boolean verifySha(String filePath, String expectedHash, String algorithm) throws NoSuchAlgorithmException, IOException {
        String computedHash;
        if (algorithm.equalsIgnoreCase("SHA-3-256")) {
            computedHash = resumeSHA3_256(filePath);
        } else if (algorithm.equalsIgnoreCase("SHA-3-512")) {
            computedHash = resumeSHA3_512(filePath);
        } else {
            computedHash = hashFile(filePath, algorithm);
        }
        return computedHash.equalsIgnoreCase(expectedHash);
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

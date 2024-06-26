/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.hashes;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.jcajce.provider.digest.SHA3;

/**
 *
 * @author Eugenio
 */
public class Sha {

    // Resumir archivo usando SHA-1
    public String resumeSHA1(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-1");
    }

    // Resumir archivo usando SHA-256 (parte de la familia SHA-2)
    public String resumeSHA256(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-256");
    }

    // Resumir archivo usando SHA-512 (parte de la familia SHA-2)
    public String resumeSHA512(String filePath) throws NoSuchAlgorithmException, IOException {
        return hashFile(filePath, "SHA-512");
    }

    // Resumir archivo usando SHA-3 (256 bits)
    public String resumeSHA3_256(String filePath) throws IOException {
        return hashFileSHA3(filePath, 256);
    }

    // Resumir archivo usando SHA-3 (512 bits)
    public String resumeSHA3_512(String filePath) throws IOException {
        return hashFileSHA3(filePath, 512);
    }

    // Método genérico para SHA-1 y SHA-2
    private String hashFile(String filePath, String algorithm) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return hashFileWithDigest(filePath, digest);
    }

    // Método específico para SHA-3 (usando Bouncy Castle)
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

    // Método genérico para calcular hash de archivo usando un MessageDigest dado
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

    // Verificación de hash de archivo
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

    // Convertir bytes a una cadena hexadecimal
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}


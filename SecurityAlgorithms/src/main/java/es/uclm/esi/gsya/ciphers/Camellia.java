/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.ciphers;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Security;
import java.util.Arrays;

/**
 *
 * @author Eugenio
 */

/*
 * Modos y Padding soportados por Camellia:
 *
 * Modes:
 * ECB
 *  - NoPadding
 *  - PKCS5Padding
 *  - ISO10126Padding
 * CBC
 *  - NoPadding
 *  - PKCS5Padding
 *  - ISO10126Padding
 * CFB
 *  - NoPadding
 * OFB
 *  - NoPadding
 * CTR
 *  - NoPadding
 * GCM
 *  - NoPadding
 * CCM
 *  - NoPadding
 * EAX
 *  - NoPadding
 * OCB
 *  - NoPadding
 * XTS
 *  - NoPadding
 *
 * Notas:
 * - ECB: Menos seguro debido a la igualdad de cifrados para bloques idénticos de texto plano.
 * - CBC: Adecuado para la mayoría de las aplicaciones que requieren seguridad mejorada respecto a ECB.
 * - CFB, OFB, CTR: Modos que permiten operar sobre flujos de datos y no requieren padding.
 * - GCM, CCM, EAX, OCB: Modos que proporcionan autenticación de mensaje junto con cifrado.
 * - XTS: Especializado para cifrado de datos de almacenamiento como discos.
 */

public class Camellia {
    private final String transformation; // Modo y Padding (e.g., "Camellia/ECB/PKCS5Padding")
    private final int keySize; // Tamaño de la clave en bits
    private SecretKey secretKey; // Clave secreta generada
    private final Cipher cipher; // Cifrador

    private static final int BUFFER_SIZE = 1024; // Tamaño del buffer para lectura/escritura de bloques

    public Camellia(String mode, String padding, int keySize) throws Exception {
        this.transformation = "Camellia/" + mode + "/" + padding;
        this.keySize = keySize;

        // Verificar que el tamaño de la clave es válido
        if (keySize != 128 && keySize != 192 && keySize != 256) {
            throw new IllegalArgumentException("El tamaño de la clave debe ser 128, 192 o 256 bits.");
        }

        // Añadir BouncyCastle como proveedor de seguridad
        Security.addProvider(new BouncyCastleProvider());

        // Generar la clave
        KeyGenerator keyGen = KeyGenerator.getInstance("Camellia", "BC");
        keyGen.init(keySize);
        this.secretKey = keyGen.generateKey();

        // Inicializar el cifrador
        this.cipher = Cipher.getInstance(this.transformation, "BC");
    }

    // Método para cifrar un archivo
    public void encryptFile(String inputFilePath, String outputFilePath) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        processFile(inputFilePath, outputFilePath);
    }

    // Método para descifrar un archivo
    public void decryptFile(String inputFilePath, String outputFilePath) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        processFile(inputFilePath, outputFilePath);
    }

    // Método privado para procesar el archivo
    private void processFile(String inputFilePath, String outputFilePath) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // Leer y cifrar/descifrar el archivo en bloques
            while ((bytesRead = fis.read(buffer)) != -1) {
                // Si leemos menos de BUFFER_SIZE bytes, debemos ajustar el tamaño del buffer
                byte[] bytesToProcess;
                if (bytesRead < BUFFER_SIZE) {
                    bytesToProcess = Arrays.copyOf(buffer, bytesRead);
                } else {
                    bytesToProcess = buffer;
                }

                byte[] processedBytes = cipher.doFinal(bytesToProcess);
                fos.write(processedBytes);
            }
        } catch (IOException e) {
            throw new Exception("Error al procesar el archivo: " + e.getMessage(), e);
        }
    }
}

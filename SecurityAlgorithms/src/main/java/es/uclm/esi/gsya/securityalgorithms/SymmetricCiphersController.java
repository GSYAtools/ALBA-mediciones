/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.ciphers.symmetric.Aes;
import es.uclm.esi.gsya.ciphers.symmetric.Camellia;
import es.uclm.esi.gsya.ciphers.symmetric.ChaCha20;
import static es.uclm.esi.gsya.securityalgorithms.OptionValues.*;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controlador que proporciona métodos para ejecutar y gestionar algoritmos de cifrado simétrico
 * como AES, Camellia y ChaCha20. También incluye métodos auxiliares para mostrar medidas de ejecución.
 * 
 * <p>Utiliza la clase Measure para calcular el tamaño de archivos y el tiempo de ejecución.</p>
 * 
 * @author Eugenio
 */
public class SymmetricCiphersController {
    
    /**
     * Muestra medidas de tamaño de archivo y tiempo de ejecución.
     * 
     * @param inputPath Ruta del archivo de entrada.
     * @param outputPath Ruta del archivo de salida.
     * @throws IOException Si ocurre un error al calcular el tamaño o la compresión.
     */
    private static void showMeasures(String inputPath, String outputPath) throws IOException{
        long fi = Measure.getFileSize(inputPath);
        long fo = Measure.getFileSize(outputPath);
        long cp = Measure.calculateCompression(fi, fo);
        System.out.printf("Input File size: %d bytes\n", fi);
        System.out.printf("Output File Size: %d bytes\n", fo);
        System.out.printf("Compression Percentage: %d %%\n", cp);
        System.out.printf("Execution Time: %d ns (%d ms)\n\n", Measure.getLastCpuTimeMeasure(), Measure.getLastCpuTimeMeasure()/1000000);
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE AES
    */
    
    /**
     * Ejecuta AES con una clave generada aleatoriamente y muestra el nombre del archivo de clave.
     * 
     * @param key Longitud de la clave AES.
     */
    public static void runAes(int key){
        Aes aes = new Aes(key);
        System.out.printf("\nKey File Successfully Generated with name: %s\n",aes.getKeyFileName());
    }
    
    /**
     * Ejecuta AES para cifrar o descifrar un archivo especificado.
     * 
     * @param operation Operación a realizar (encriptar o desencriptar).
     * @param mode Modo de cifrado (por ejemplo, ECB, CBC).
     * @param padding Relleno utilizado (por ejemplo, PKCS5Padding).
     * @param key Clave de cifrado.
     * @param input Ruta del archivo de entrada.
     * @param output Ruta del archivo de salida.
     */
    public static void runAes(String operation, String mode, String padding, String key, String input, String output, int iterations) {
        Aes aes = new Aes(mode, padding, key);
        if(OP_ENCRYPT.equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                Measure.call(2000);
                for(int i=0; i<iterations;i++)
                    aes.encryptFile(new File(input), new File(output));
                Measure.call(5000);
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Successfully Encrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(OP_DECRYPT.equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                Measure.call(2000);
                for(int i=0; i<iterations;i++)
                    aes.decryptFile(new File(input), new File(output));
                Measure.call(5000);
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Successfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE CAMELLIA
    */
    
    /**
     * Ejecuta Camellia con una clave generada aleatoriamente y muestra el nombre del archivo de clave.
     * 
     * @param key Longitud de la clave Camellia.
     */
    public static void runCamellia(int key){
        try {
            Camellia camellia = new Camellia(key);
            System.out.printf("\nKey File Successfully Generated with name: %s\n",camellia.getKeyFileName());
        } catch (NoSuchProviderException | IOException ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Ejecuta Camellia para cifrar o descifrar un archivo especificado.
     * 
     * @param operation Operación a realizar (encriptar o desencriptar).
     * @param mode Modo de cifrado (por ejemplo, ECB, CBC).
     * @param padding Relleno utilizado (por ejemplo, PKCS5Padding).
     * @param key Clave de cifrado.
     * @param input Ruta del archivo de entrada.
     * @param output Ruta del archivo de salida.
     */
    public static void runCamellia(String operation, String mode, String padding, String key, String input, String output) {
        Camellia camellia;
        try {
            camellia = new Camellia(mode, padding, key);
        } catch (IOException ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if(OP_ENCRYPT.equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                camellia.encryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Successfully Encrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if(OP_DECRYPT.equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                camellia.decryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Successfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE CHACHA20
    */
    
    /**
     * Ejecuta ChaCha20 para generar una clave y un nonce, mostrando sus nombres de archivo.
     * 
     * @param algorithm Algoritmo ChaCha20 a utilizar.
     */
    public static void runChaCha20(String algorithm){
        ChaCha20 chacha = new ChaCha20(algorithm);
        System.out.printf("\nKey File Successfully Generated with name: %s\n", chacha.getKeyFileName());
    }
    
    /**
     * Ejecuta ChaCha20 para cifrar o descifrar un archivo especificado.
     * 
     * @param operation Operación a realizar (encriptar o desencriptar).
     * @param algorithm Algoritmo ChaCha20 a utilizar.
     * @param key Clave de cifrado.
     * @param input Ruta del archivo de entrada.
     * @param output Ruta del archivo de salida.
     * @throws Exception Si ocurre un error durante la operación de cifrado o descifrado.
     */
    public static void runChaCha20(String operation,String algorithm, String key, String input, String output) throws Exception {
        ChaCha20 chacha = new ChaCha20(key, algorithm);
        if(OP_ENCRYPT.equalsIgnoreCase(operation)){
            chacha.encrypt(Paths.get(input), Paths.get(output));
        }else if(OP_DECRYPT.equalsIgnoreCase(operation)){
            chacha.decrypt(Paths.get(input), Paths.get(output));
        }
    }
    
}

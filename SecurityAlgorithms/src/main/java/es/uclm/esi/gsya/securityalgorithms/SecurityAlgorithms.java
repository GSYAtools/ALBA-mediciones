/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.ciphers.Aes;
import es.uclm.esi.gsya.ciphers.Camellia;
import java.io.File;

/**
 *
 * @author Eugenio
 */
public class SecurityAlgorithms { 
    private static String runMode, inputPath, outputPath, algorithm, keyPath;
    private static int keySize, times;
    
    public static void main(String[] args) {
        /* Processing arguments */
        if(args.length <= 0) return;
        runMode = args[0];
        switch(runMode){
            case "e" -> {
                inputPath = args[1];
                outputPath = args[2];
                algorithm = args[3];
                keyPath = args[4];
                times = Integer.parseInt(args[5]);
            }
            case "d" -> {
                inputPath = args[1];
                outputPath = args[2];
                algorithm = args[3];
                keyPath = args[4];
                times = Integer.parseInt(args[5]);
            }
            case "g" -> {
                algorithm = args[1];
                keySize = Integer.parseInt(args[2]);
            }
        }
        
        /* Processing algorithm */
            /* This should be an string like AES-CBC-PKCS7Padding */
        String[] algData = algorithm.split("-");
        String algName, algMode, algPadding;
        if(algData.length == 1){
            algName = algorithm; algMode = null; algPadding = null;
        } else {
            algName = algData[0]; algMode = algData[1]; algPadding = algData[2];
        }
        /* Launch algorithm */
        switch(algName){
            case "AES" -> runAes(algMode, algPadding);
            case "Camellia" -> runCamellia(algMode, algPadding, Integer.parseInt(algData[3]));
            default -> System.out.println("Algoritmo No Encontrado");
        }
    }
    
    private static void runAes(String mode, String padding){
        try {
            File inputFile = null;
            File outputFile = null;
            /* Check files */
            if (!runMode.equals("g")) {
                inputFile = new File(inputPath);
                outputFile = new File(outputPath);
            }
            
            switch(runMode){
                case "e" -> {
                    Aes aes = new Aes(mode, padding, keyPath);
                    aes.encryptFile(inputFile, outputFile);
                    System.out.println("File Encrypted Successfully.");
                }
                case "d" -> {
                    Aes aes = new Aes(mode, padding, keyPath);
                    aes.decryptFile(inputFile, outputFile);
                    System.out.println("File Decrypted Successfully.");
                }
                case "g" -> {
                    Aes aes = new Aes(keySize);
                    System.out.println("Key File Generated Successfully.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void runCamellia(String mode, String padding, int keySize){
        try {
            /* Check files */
            File inputFile = new File(inputPath);
            File encryptedFile = new File(outputPath);
            
            /* Init instance*/
            Camellia camellia = new Camellia(mode, padding, keySize);

            /* Encrypt */
            camellia.encryptFile(inputFile, encryptedFile);

            System.out.println("File Encrypted Successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

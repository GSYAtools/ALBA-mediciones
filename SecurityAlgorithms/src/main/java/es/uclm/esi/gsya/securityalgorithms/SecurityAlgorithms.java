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

    private static String inputPath;
    private static String outputPath;
    private static String algorithmInfo;
    
    public static void main(String[] args) {
        /* Processing arguments */
        if(args.length != 3) return;
        inputPath = args[0];
        outputPath = args[1];
        algorithmInfo = args[2];
        
        /* Processing algorithm */
            /* This should be an string like AES-CBC-PKCS7Padding-256 */
        String[] algData = algorithmInfo.split("-");
        /* Launch algorithm */
        switch(algData[0]){
            case "AES" -> runAes(algData[1], algData[2], Integer.parseInt(algData[3]));
            case "Camellia" -> runCamellia(algData[1], algData[2], Integer.parseInt(algData[3]));
            default -> System.out.println("Algoritmo No Encontrado");
        }
    }
    
    private static void runAes(String mode, String padding, int keySize){
        try {
            /* Check files */
            File inputFile = new File(inputPath);
            File encryptedFile = new File(outputPath);
            
            /* Init instance*/
            Aes aes = new Aes(mode, padding, keySize);

            /* Encrypt */
            aes.encryptFile(inputFile, encryptedFile);

            System.out.println("File Encrypted Successfully.");
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

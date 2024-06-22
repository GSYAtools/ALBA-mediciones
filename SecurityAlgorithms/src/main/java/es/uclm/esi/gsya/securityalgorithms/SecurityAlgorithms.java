/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.ciphers.Aes;
import es.uclm.esi.gsya.ciphers.Camellia;
import es.uclm.esi.gsya.ciphers.ChaCha20;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;

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
        }else if(algData.length == 2){
            algName = algData[0]; algMode = algData[1]; algPadding = null;
        } else {
            algName = algData[0]; algMode = algData[1]; algPadding = algData[2];
        }
        /* Launch algorithm */
        switch(algName){
            case "AES" : runAes(algMode, algPadding); break;
            case "Camellia" : runCamellia(algMode, algPadding); break;
            case "ChaCha20" :
            case "XChaCha20" : runChaCha20(algName, algMode); break;
            default : System.out.println("Algoritmo No Encontrado"); break;
        }
    }
    
    
    private static void showMeasures() throws IOException{
        long fi = Measure.getFileSize(inputPath);
        long fo = Measure.getFileSize(outputPath);
        long cp = Measure.calculateCompression(fi, fo);
        System.out.printf("Input File size: %d bytes\n", fi);
        System.out.printf("Output File Size: %d bytes\n", fo);
        System.out.printf("Compression Percentage: %d %%\n", cp);
        System.out.printf("Execution Time: %d ns\n", Measure.getLastTimeMeasure());
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
                    for(int t=0; t<times; t++){
                        Measure.getStartTime();
                        aes.encryptFile(inputFile, outputFile);
                        Measure.getEndTime();
                        System.out.printf("File Encrypted Successfully. [%d]\n", t+1);
                        showMeasures();
                    }
                }
                case "d" -> {
                    Aes aes = new Aes(mode, padding, keyPath);
                    for(int t=0; t<times;t++){
                        aes.decryptFile(inputFile, outputFile);
                        System.out.printf("File Decrypted Successfully.[%d]\n", t+1);
                    }
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
    
    private static void runCamellia(String mode, String padding){
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
                    Camellia camellia = new Camellia(mode, padding, keyPath);
                    for(int t=0; t<times; t++){
                        camellia.encryptFile(inputFile, outputFile);
                        System.out.printf("File Encrypted Successfully. [%d]\n", t);
                    }
                }
                case "d" -> {
                    Camellia camellia = new Camellia(mode, padding, keyPath);
                    for(int t=0; t<times; t++){
                        camellia.decryptFile(inputFile, outputFile);
                        System.out.printf("File Encrypted Successfully. [%d]\n", t);
                    }
                }
                case "g" -> {
                    Camellia camellia = new Camellia(keySize);
                    System.out.println("Key File Generated Successfully.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void runChaCha20(String name, String mode) {
        try {
            File inputFile = new File(inputPath);
            File outputFile = new File(outputPath);

            // Según el modo de ejecución (cifrado, descifrado o generación de clave)
            switch (runMode) {
                case "e" -> {
                    ChaCha20 chacha = new ChaCha20(mode, keyPath);
                    for (int t = 0; t < times; t++) {
                        chacha.encryptFile(inputFile, outputFile);
                        System.out.printf("File Encrypted Successfully. [%d]\n", t);
                    }
                }
                case "d" -> {
                    ChaCha20 chacha = new ChaCha20(mode, keyPath);
                    for (int t = 0; t < times; t++) {
                        chacha.decryptFile(inputFile, outputFile);
                        System.out.printf("File Decrypted Successfully. [%d]\n", t);
                    }
                }
                case "g" -> {
                    ChaCha20 chacha = new ChaCha20(); //Siempre la clave será de 256 bits
                    System.out.println("Key File Generated Successfully.");
                }
                default -> System.out.println("Invalid run mode.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}

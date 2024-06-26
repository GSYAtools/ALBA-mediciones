/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.ciphers.symmetric.Aes;
import es.uclm.esi.gsya.ciphers.symmetric.Camellia;
import es.uclm.esi.gsya.ciphers.symmetric.ChaCha20;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eugenio
 */
public class SymmetricCiphersController {
    
    private static void showMeasures(String inputPath, String outputPath) throws IOException{
        long fi = Measure.getFileSize(inputPath);
        long fo = Measure.getFileSize(outputPath);
        long cp = Measure.calculateCompression(fi, fo);
        System.out.printf("Input File size: %d bytes\n", fi);
        System.out.printf("Output File Size: %d bytes\n", fo);
        System.out.printf("Compression Percentage: %d %%\n", cp);
        System.out.printf("Execution Time: %d ns (%d ms)\n", Measure.getLastCpuTimeMeasure(), Measure.getLastCpuTimeMeasure()/1000000);
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE AES
    */
    public static void runAes(int key){
        Aes aes = new Aes(key);
        System.out.printf("\nKey File Successfully Generated with name: %s\n",aes.getKeyFileName());
    }
    
    public static void runAes(String operation, String mode, String padding, String key, String input, String output) {
        Aes aes = new Aes(mode, padding, key);
        if("encrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                aes.encryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Encrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                aes.decryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void runAes(String operation, String mode, String padding, String key, String input, String output, int times) {
        Aes aes = new Aes(mode, padding, key);
        if("encrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    aes.encryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Encrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    aes.decryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Decrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE CAMELLIA
    */
    public static void runCamellia(int key){
        try {
            Camellia camellia = new Camellia(key);
            System.out.printf("\nKey File Successfully Generated with name: %s\n",camellia.getKeyFileName());
        } catch (NoSuchProviderException | IOException ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void runCamellia(String operation, String mode, String padding, String key, String input, String output) {
        Camellia camellia;
        try {
            camellia = new Camellia(mode, padding, key);
        } catch (IOException ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if("encrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                camellia.encryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Encrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                camellia.decryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void runCamellia(String operation, String mode, String padding, String key, String input, String output, int times) {
        Camellia camellia;
        try {
            camellia = new Camellia(mode, padding, key);
        } catch (IOException ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if("encrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    camellia.encryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Encrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    camellia.decryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Decrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE CHACHA20
    */
    public static void runChaCha20(){
        ChaCha20 chacha = new ChaCha20();
        System.out.printf("\nKey File Successfully Generated with name: %s\n", chacha.getKeyFileName());
    }
    
    public static void runChaCha20(String operation, String mode, String key, String input, String output) {
        ChaCha20 chacha;
        try {
            chacha = new ChaCha20(mode, key);
        } catch (Exception ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if("encrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                chacha.encryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Encrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                chacha.decryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void runChaCha20(String operation, String mode, String key, String input, String output, int times) {
        ChaCha20 chacha;
        try {
            chacha = new ChaCha20(mode, key);
        } catch (Exception ex) {
            Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        if("encrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    chacha.encryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Encrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            for(int i=1; i<=times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    chacha.decryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Decrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(SymmetricCiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

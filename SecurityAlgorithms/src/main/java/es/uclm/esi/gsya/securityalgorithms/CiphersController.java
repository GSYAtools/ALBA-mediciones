/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.ciphers.Aes;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eugenio
 */
public class CiphersController {
    
    private static void showMeasures(String inputPath, String outputPath) throws IOException{
        long fi = Measure.getFileSize(inputPath);
        long fo = Measure.getFileSize(outputPath);
        long cp = Measure.calculateCompression(fi, fo);
        System.out.printf("Input File size: %d bytes\n", fi);
        System.out.printf("Output File Size: %d bytes\n", fo);
        System.out.printf("Compression Percentage: %d %%\n", cp);
        System.out.printf("Execution Time: %d ns\n", Measure.getLastCpuTimeMeasure());
    }
    
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
                Logger.getLogger(CiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            try {
                Measure.startCPUMeasurement();
                aes.decryptFile(new File(input), new File(output));
                Measure.stopCPUMeasurement();
                System.out.printf("\nFile Succesfully Decrypted.\n");
                showMeasures(input, output);
            } catch (Exception ex) {
                Logger.getLogger(CiphersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void runAes(String operation, String mode, String padding, String key, String input, String output, int times) {
        Aes aes = new Aes(mode, padding, key);
        if("encrypt".equalsIgnoreCase(operation)){
            for(int i=0; i<times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    aes.encryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Encrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(CiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else if("decrypt".equalsIgnoreCase(operation)){
            for(int i=0; i<times;i++) {
                try {
                    Measure.startCPUMeasurement();
                    aes.decryptFile(new File(input), new File(output));
                    Measure.stopCPUMeasurement();
                    System.out.printf("\n[Execution #%d] File Succesfully Decrypted.\n", i);
                    showMeasures(input, output);
                } catch (Exception ex) {
                    Logger.getLogger(CiphersController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.hashes.Md5;
import es.uclm.esi.gsya.hashes.Sha;
import es.uclm.esi.gsya.utils.FileHandler;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alarcos
 */
public class HashesController {
    
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
    MÉTODOS PARA LA EJECUCIÓN DE MD5
    */
    public static void runMd5(String input){
        String hashFileName = "md5.txt";
        //Obtengo la instancia
        Md5 md5 = new Md5();
        
        //Genero el resumen
        Measure.startCPUMeasurement();
        String hash = md5.generateMd5(new File(input));
        Measure.stopCPUMeasurement();
        
        try {
            FileHandler.saveTextToFile(hashFileName, hash);
            System.out.println("MD5 hash saved into: "+hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not save MD5 hash to file.");
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }
    
    public static void runMd5(String input, String hashFile){
        String hash;
        try {
            //Método para verificar
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read MD5 hash from file.");
            return;
        }
        
        Md5 md5 = new Md5();
        
        boolean res = md5.verifyMd5(new File(input), hash);
        System.out.println("Input file matches hash: "+res);
    }
    
    public static void runSHA_1(String input) throws NoSuchAlgorithmException, IOException{
        String hashFileName = "sha_1.txt";
        //Obtengo la instancia
        Sha sha = new Sha();
        
        //Genero el resumen
        Measure.startCPUMeasurement();
        String hash = sha.resumeSHA1(input);
        Measure.stopCPUMeasurement();
        
        try {
            FileHandler.saveTextToFile(hashFileName, hash);
            System.out.println("SHA-1 hash saved into: "+hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not save SHA-1 hash to file.");
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }
    
    public static void runSHA_1(String input, String hashFile) throws NoSuchAlgorithmException, IOException{
        String hash;
        try {
            //Método para verificar
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read SHA-1 hash from file.");
            return;
        }
        
        Sha sha = new Sha();
        
        boolean res = sha.verifySha(input, hash, "SHA-1");
        System.out.println("Input file matches hash: "+res);
    }

    static void runSHA_2(String input, int mode) throws NoSuchAlgorithmException, IOException {
        String hashFileName = "sha_2.txt";
        //Obtengo la instancia
        Sha sha = new Sha();
        
        //Genero el resumen
        String hash = null;
        switch(mode){
            case 256 -> {
                Measure.startCPUMeasurement();
                hash = sha.resumeSHA256(input);
                Measure.stopCPUMeasurement();
            }
            case 512 -> {
                Measure.startCPUMeasurement();
                hash = sha.resumeSHA512(input);
                Measure.stopCPUMeasurement();
            }
        }
        
        
        try {
            FileHandler.saveTextToFile(hashFileName, hash);
            System.out.println("SHA-2 hash saved into: "+hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not save SHA-2 hash to file.");
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }

    static void runSHA_2(String input, int mode, String hashFile) throws NoSuchAlgorithmException, IOException {
        String hash;
        try {
            //Método para verificar
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read SHA-2 hash from file.");
            return;
        }
        
        Sha sha = new Sha();
        boolean res = false;
        switch(mode){
            case 256 -> {
                res = sha.verifySha(input, hash, "SHA-256");
            }
            case 512 -> {
                res = sha.verifySha(input, hash, "SHA-512");
            }
        }
        
        System.out.println("Input file matches hash: "+res);
    }

    static void runSHA_3(String input, int mode) throws IOException {
        String hashFileName = "sha_3.txt";
        //Obtengo la instancia
        Sha sha = new Sha();
        
        //Genero el resumen
        String hash = null;
        switch(mode){
            case 256 -> {
                Measure.startCPUMeasurement();
                hash = sha.resumeSHA3_256(input);
                Measure.stopCPUMeasurement();
            }
            case 512 -> {
                Measure.startCPUMeasurement();
                hash = sha.resumeSHA3_512(input);
                Measure.stopCPUMeasurement();
            }
        }
        
        
        try {
            FileHandler.saveTextToFile(hashFileName, hash);
            System.out.println("SHA-3 hash saved into: "+hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not save SHA-3 hash to file.");
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }

    static void runSHA_3(String input, int mode, String hashFile) throws NoSuchAlgorithmException, IOException {
        String hash;
        try {
            //Método para verificar
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read SHA-3 hash from file.");
            return;
        }
        
        Sha sha = new Sha();
        boolean res = false;
        switch(mode){
            case 256 -> {
                res = sha.verifySha(input, hash, "SHA-3-256");
            }
            case 512 -> {
                res = sha.verifySha(input, hash, "SHA-3-512");
            }
        }
        
        System.out.println("Input file matches hash: "+res);
    }
}

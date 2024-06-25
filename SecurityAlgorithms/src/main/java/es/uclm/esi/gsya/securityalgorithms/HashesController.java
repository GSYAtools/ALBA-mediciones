/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.hashes.Md5;
import es.uclm.esi.gsya.utils.FileHandler;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
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
        System.out.printf("Execution Time: %d ns (%d ms)\n", Measure.getLastCpuTimeMeasure(), Measure.getLastCpuTimeMeasure()/1000000);
    }
    
    /*
    MÉTODOS PARA LA EJECUCIÓN DE MD5
    */
    public static void runMd5(String input){
        String hashFileName = "md5.txt";
        //Obtengo la instancia
        Md5 md5 = new Md5();
        
        //Genero el resumen
        String hash = md5.generateMd5(new File(input));
        
        try {
            FileHandler.saveTextToFile(hashFileName, hash);
            System.out.println("MD5 hash saved into: "+hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not save MD5 hash to file.");
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
        
        md5.verifyMd5(new File(input), hash);
    }
}

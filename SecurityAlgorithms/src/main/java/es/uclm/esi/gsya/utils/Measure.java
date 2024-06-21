/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 *
 * @author Eugenio
 */
public class Measure {
    private static long sTime, eTime;
    
    public static void getStartTime(){
        sTime = System.nanoTime();
    }
    
    public static void getEndTime(){
        eTime = System.nanoTime();
    }
    
    public static long getLastTimeMeasure(){
        return eTime - sTime;
    }
    
    public static long getFileSize(String path) throws IOException{
        return Files.size(Path.of(path));
    }
    
    public static long calculateCompression(long original, long compressed){
        return (compressed / original - 1) * -100;
    }
}

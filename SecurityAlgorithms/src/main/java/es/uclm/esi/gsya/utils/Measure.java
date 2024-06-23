/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
/**
 *
 * @author Eugenio
 */
public class Measure {
    private static long sTime, eTime;
    private static ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private static long CPUsTime, CPUeTime;
    
    public static long getLastTimeMeasure(){
        return eTime - sTime;
    }
    
    public static void startCPUMeasurement() {
        CPUsTime = threadMXBean.getCurrentThreadCpuTime();
    }
    
    public static void stopCPUMeasurement() {
        CPUeTime = threadMXBean.getCurrentThreadCpuTime();
    }
    
    public static long getLastCpuTimeMeasure(){
        return CPUeTime - CPUsTime;
    }
    
    public static long getFileSize(String path) throws IOException{
        return Files.size(Path.of(path));
    }
    
    public static long calculateCompression(long original, long compressed){
        return (compressed / original - 1) * -100;
    }
}

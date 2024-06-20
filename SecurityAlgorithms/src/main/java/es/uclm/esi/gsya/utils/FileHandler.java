/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author Eugenio
 */
public class FileHandler {
    public static final String KEY_PATH = "clave.key";
    
    public static void saveKeyToFile(String filePath, byte[] keyBytes) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(keyBytes);
        }
    }
    
    public static byte[] readKeyFromFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] keyBytes = new byte[fis.available()];
            fis.read(keyBytes);
            return keyBytes;
        }
    }
}

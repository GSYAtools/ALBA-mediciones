/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 *
 * @author Eugenio
 */
public class FileHandler {   
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
    
    public static void saveTextToFile(String filePath, String text) throws IOException{
        try (FileOutputStream fos = new FileOutputStream(filePath);
             OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8")) {
            writer.write(text);
        }
    }
    
    public static String readTextFromFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filePath);
             InputStreamReader reader = new InputStreamReader(fis, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            char[] buffer = new char[1024];
            int bytesRead;
            // Leer el archivo en bloques de caracteres
            while ((bytesRead = bufferedReader.read(buffer, 0, buffer.length)) != -1) {
                // Agregar los caracteres leídos al StringBuilder
                content.append(buffer, 0, bytesRead);
            }
        }
        return content.toString(); // Retornar el contenido como String
    }
}

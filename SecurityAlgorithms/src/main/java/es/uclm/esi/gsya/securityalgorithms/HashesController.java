
package es.uclm.esi.gsya.securityalgorithms;

import es.uclm.esi.gsya.hashes.Md5;
import es.uclm.esi.gsya.hashes.Ripemd160;
import es.uclm.esi.gsya.hashes.Sha;
import es.uclm.esi.gsya.hashes.Whirpool;
import es.uclm.esi.gsya.utils.FileHandler;
import es.uclm.esi.gsya.utils.Measure;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase {@code HashesController} proporciona métodos para calcular y verificar 
 * hashes utilizando varios algoritmos de resumen de mensajes, como MD5, SHA-1, SHA-2, 
 * SHA-3, RIPEMD-160 y Whirlpool. También permite mostrar las medidas de rendimiento 
 * y compresión de los archivos procesados.
 * <p>
 * Esta clase utiliza utilidades como {@link Measure} para medir el tiempo de ejecución 
 * y el tamaño de archivos, y {@link FileHandler} para manejar operaciones de lectura 
 * y escritura de archivos.
 * </p>
 * 
 * @author Eugenio
 */
public class HashesController {
    /**
     * Muestra las medidas de tamaño y tiempo de ejecución para los archivos de entrada 
     * y salida, y calcula el porcentaje de compresión.
     *
     * @param inputPath  La ruta del archivo de entrada.
     * @param outputPath La ruta del archivo de salida.
     * @throws IOException Si ocurre un error al leer los archivos.
     */
    private static void showMeasures(String inputPath, String outputPath) throws IOException{
        long fi = Measure.getFileSize(inputPath);
        long fo = Measure.getFileSize(outputPath);
        long cp = Measure.calculateCompression(fi, fo);
        System.out.printf("Input File size: %d bytes\n", fi);
        System.out.printf("Output File Size: %d bytes\n", fo);
        System.out.printf("Compression Percentage: %d %%\n", cp);
        System.out.printf("Execution Time: %d ns (%d ms)\n\n", Measure.getLastCpuTimeMeasure(), Measure.getLastCpuTimeMeasure()/1000000);
    }
    
    /**
    * Genera y guarda el hash MD5 de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
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
    
    /**
    * Verifica si el hash MD5 guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param hashFile La ruta del archivo que contiene el hash MD5 guardado.
    */
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
    
    /**
    * Genera y guarda el hash SHA-1 de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
    * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-1.
    * @throws IOException              Si ocurre un error al guardar el hash en un archivo.
    */
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
    
    /**
    * Verifica si el hash SHA-1 guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param hashFile La ruta del archivo que contiene el hash SHA-1 guardado.
    * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-1.
    * @throws IOException              Si ocurre un error al leer el hash desde el archivo.
    */
   public static void runSHA_1(String input, String hashFile) throws NoSuchAlgorithmException, IOException {

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

    /**
    * Genera y guarda el hash SHA-2 (SHA-256 o SHA-512) de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
    * @param mode  El modo de SHA-2 a usar (256 para SHA-256 o 512 para SHA-512).
    * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-2.
    * @throws IOException              Si ocurre un error al guardar el hash en un archivo.
    */
   public static void runSHA_2(String input, int mode) throws NoSuchAlgorithmException, IOException {

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

    /**
    * Verifica si el hash SHA-2 (SHA-256 o SHA-512) guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param mode     El modo de SHA-2 a usar (256 para SHA-256 o 512 para SHA-512).
    * @param hashFile La ruta del archivo que contiene el hash SHA-2 guardado.
    * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-2.
    * @throws IOException              Si ocurre un error al leer el hash desde el archivo.
    */
   public static void runSHA_2(String input, int mode, String hashFile) throws NoSuchAlgorithmException, IOException {

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

    /**
    * Genera y guarda el hash SHA-3 (SHA-3-256 o SHA-3-512) de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
    * @param mode  El modo de SHA-3 a usar (256 para SHA-3-256 o 512 para SHA-3-512).
    * @throws IOException Si ocurre un error al guardar el hash en un archivo o al leer los archivos.
    */
    public static void runSHA_3(String input, int mode) throws IOException {

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

    /**
    * Verifica si el hash SHA-3 (SHA-3-256 o SHA-3-512) guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param mode     El modo de SHA-3 a usar (256 para SHA-3-256 o 512 para SHA-3-512).
    * @param hashFile La ruta del archivo que contiene el hash SHA-3 guardado.
    * @throws NoSuchAlgorithmException Si no se encuentra el algoritmo SHA-3.
    * @throws IOException              Si ocurre un error al leer el hash desde el archivo.
    */
    public static void runSHA_3(String input, int mode, String hashFile) throws NoSuchAlgorithmException, IOException {

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
    
    /**
    * Genera y guarda el hash RIPEMD-160 de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
    */
    public static void runRIPEMD_160(String input) {

        Ripemd160 ripemd160 = new Ripemd160();
        String hashFileName = "ripemd160.txt";
        try {
            Measure.startCPUMeasurement();
            String hash = ripemd160.resume(input);
            Measure.stopCPUMeasurement();
            FileHandler.saveTextToFile(hashFileName, hash);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }
    
    /**
    * Verifica si el hash RIPEMD-160 guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param hashFile La ruta del archivo que contiene el hash RIPEMD-160 guardado.
    */
    public static void runRIPEMD_160(String input, String hashFile){
        String hash;
        try {
            //Método para verificar
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read MD5 hash from file.");
            return;
        }
        
        try {
            Ripemd160 ripemd160 = new Ripemd160();
            boolean res = ripemd160.verify(input, hash);
            System.out.println("Input file matches hash: "+res);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
    
    /**
    * Genera y guarda el hash Whirlpool de un archivo de entrada.
    *
    * @param input La ruta del archivo de entrada.
    */
    public static void runWHIRPOOL(String input){
        Whirpool whirpool = new Whirpool();
        String hashFileName = "whirpool.txt";
        try {
            Measure.startCPUMeasurement();
            String hash = whirpool.resume(input);
            Measure.stopCPUMeasurement();
            FileHandler.saveTextToFile(hashFileName, hash);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
        try {
            showMeasures(input, hashFileName);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read files to show measures");
        }
    }
    
    /**
    * Verifica si el hash Whirlpool guardado en un archivo coincide con el hash del archivo de entrada.
    *
    * @param input    La ruta del archivo de entrada.
    * @param hashFile La ruta del archivo que contiene el hash Whirlpool guardado.
    */
    public static void runWHIRPOOL(String input, String hashFile){
        String hash;
        try {
            hash = FileHandler.readTextFromFile(hashFile);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not read MD5 hash from file.");
            return;
        }
        
        try {
            Whirpool whirpool = new Whirpool();
            boolean res = whirpool.verify(input, hash);
            System.out.println("Input file matches hash: "+res);
        } catch (IOException ex) {
            Logger.getLogger(HashesController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        
    }
}

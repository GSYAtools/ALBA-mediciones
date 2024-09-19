
package es.uclm.esi.gsya.securityalgorithms;

import static es.uclm.esi.gsya.securityalgorithms.OptionValues.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;

/**
 *
 * @author Eugenio
 */
public class SecurityAlgorithmsCLI { 
    private static int iterations = 1;
    public static void main(String[] args) {
        // Define las opciones de línea de comandos
        Options options = new Options();

        options.addOption(OptionValues.CLI_ALG, OptionValues.CLI_ALGORITHM, true, "Algoritmo a usar (AES, Camellia, ChaCha20, etc.)");
        options.addOption(OptionValues.CLI_OP, OptionValues.CLI_OPERATION, true, "Tipo de operación: encrypt, decrypt, keygen");
        options.addOption(OptionValues.CLI_MODE, true, "Modo de operación (para cifrados de bloque)");
        options.addOption(OptionValues.CLI_PAD, OptionValues.CLI_PADDING, true, "Padding scheme (para cifrados de bloque)");
        options.addOption(OptionValues.CLI_KEY, true, "Ruta a la clave o tamaño de clave (para generación de claves)");
        options.addOption(OptionValues.CLI_HASH, true, "Ruta al fichero donde se encuentra el hash");
        options.addOption(OptionValues.CLI_IN, OptionValues.CLI_INPUT, true, "Ruta al archivo de entrada");
        options.addOption(OptionValues.CLI_OUT, OptionValues.CLI_OUTPUT, true, "Ruta al archivo de salida");
        options.addOption(OptionValues.CLI_IT, OptionValues.CLI_ITERATE, true, "Numero de veces que se repetirá la operación");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            String operation = cmd.getOptionValue("operation");
            String algorithm = cmd.getOptionValue("algorithm");
            String mode = cmd.getOptionValue("mode");
            String padding = cmd.getOptionValue("padding");
            String keyPath = cmd.getOptionValue("key");
            String hashPath = cmd.getOptionValue("hash");
            String inputPath = cmd.getOptionValue("input");
            String outputPath = cmd.getOptionValue("output");
            String iterate = cmd.getOptionValue("iterate"); //times.matches("^[1-9]\\d*$")

            if(iterate != null)
                if(iterate.matches("^[1-9]\\\\d*$"))
                    iterations = Integer.parseInt(iterate);
            
            if (ALG_AES.equalsIgnoreCase(algorithm)) {
                try {
                    startAES(operation, keyPath, mode, padding, inputPath, outputPath);
                } catch (MissingArgumentException | FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_CAMELLIA.equalsIgnoreCase(algorithm)) {
                try {
                    startCamellia(operation, keyPath, mode, padding, inputPath, outputPath);
                } catch (MissingArgumentException | FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_CHACHA20.equalsIgnoreCase(algorithm)) {
                try {
                    startChaCha20(operation, keyPath, inputPath, outputPath);
                } catch (Exception ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_CHACHA20_POLY1305.equalsIgnoreCase(algorithm)) {
                try {
                    startChaCha20_Poly1305(operation, keyPath, inputPath, outputPath);
                } catch (Exception ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_XCHACHA20.equalsIgnoreCase(algorithm)) {
                try {
                    startXChaCha20(operation, keyPath, inputPath, outputPath);
                } catch (Exception ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if(ALG_MD5.equalsIgnoreCase(algorithm)){
                try {
                    startMD5(operation, inputPath, hashPath);
                } catch (UnsupportedOperationException | FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            }else if(ALG_SHA1.equalsIgnoreCase(algorithm)){
                try {
                    startSHA_1(operation, inputPath, hashPath);
                } catch (UnsupportedOperationException | NoSuchAlgorithmException | IOException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            }else if(ALG_SHA2.equalsIgnoreCase(algorithm)){
                try {
                    startSHA_2(operation, inputPath, mode, hashPath);
                } catch (UnsupportedOperationException | NoSuchAlgorithmException | IOException | MissingArgumentException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            }else if(ALG_SHA3.equalsIgnoreCase(algorithm)){
                try {
                    startSHA_3(operation, inputPath, mode, hashPath);
                } catch (MissingArgumentException | IOException | NoSuchAlgorithmException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_RIPEMD_160.equalsIgnoreCase(algorithm)){
                try {
                    startRIPEMD_160(operation, inputPath, hashPath);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            } else if (ALG_WHIRPOOL.equalsIgnoreCase(algorithm)){
                try {
                    startWHIRPOOL(operation, inputPath, hashPath);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            }else {
                formatter.printHelp("java -jar SecurityAlgorithms.jar", options);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("java -jar SecurityAlgorithms.jar", options);
            System.exit(1);
        }
    }
    
    /*SYMMETRIC CIPHERS METHODS*/
   
    private static void startAES(String operation, String key, String mode, String padding, String input, String output) throws MissingArgumentException, FileNotFoundException{
        if(OP_KEYGEN.equalsIgnoreCase(operation)){
            if(key == null || (!key.equals("128") && !key.equals("192") && !key.equals("256"))){throw new MissingArgumentException("Argument \"-key\" must be 128, 192 or 256");}
            SymmetricCiphersController.runAes(Integer.parseInt(key));
        }else if((OP_ENCRYPT.equalsIgnoreCase(operation) || OP_DECRYPT.equalsIgnoreCase(operation))){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
            if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
            if(mode == null){throw new MissingArgumentException("Argument \"-mode\" is mandatory for AES operation "+operation);}
            if(padding == null){throw new MissingArgumentException("Argument \"-pad\" is mandatory for AES operation "+operation);}
            if(output == null){throw new MissingArgumentException("Argument \"-out\" is mandatory for AES operation "+operation);}
            SymmetricCiphersController.runAes(operation, mode, padding, key, input, output, iterations);
        }else {
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for AES");
        }
    }
    
    private static void startCamellia(String operation, String key, String mode, String padding, String input, String output) throws MissingArgumentException, FileNotFoundException{
        if(OP_KEYGEN.equalsIgnoreCase(operation)){
            if(key == null || (!key.equals("128") && !key.equals("192") && !key.equals("256"))){throw new MissingArgumentException("Argument \"-key\" must be 128, 192 or 256");}
            SymmetricCiphersController.runCamellia(Integer.parseInt(key));
        }else if((OP_ENCRYPT.equalsIgnoreCase(operation) || OP_DECRYPT.equalsIgnoreCase(operation))){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
            if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
            if(mode == null){throw new MissingArgumentException("Argument \"-mode\" is mandatory for Camellia operation "+operation);}
            if(padding == null){throw new MissingArgumentException("Argument \"-pad\" is mandatory for Camellia operation "+operation);}
            if(output == null){throw new MissingArgumentException("Argument \"-out\" is mandatory for Camellia operation "+operation);}
            SymmetricCiphersController.runCamellia(operation, mode, padding, key, input, output);
        }else {
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for Camellia");
        }
    }
    
    private static void startChaCha20(String operation, String key, String input, String output) throws Exception{
        if(OP_KEYGEN.equalsIgnoreCase(operation)){
            SymmetricCiphersController.runChaCha20(ALG_CHACHA20);
        }else if((OP_ENCRYPT.equalsIgnoreCase(operation) || OP_DECRYPT.equalsIgnoreCase(operation))){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
            if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
            if(output == null){throw new MissingArgumentException("Argument \"-out\" is mandatory for ChaCha20 operation "+operation);}
            SymmetricCiphersController.runChaCha20(operation, ALG_CHACHA20, key, input, output);
        }else {
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for ChaCha20");
        }
    }
    
    private static void startChaCha20_Poly1305(String operation, String key, String input, String output) throws Exception{
        if(OP_KEYGEN.equalsIgnoreCase(operation)){
            SymmetricCiphersController.runChaCha20(ALG_CHACHA20_POLY1305);
        }else if((OP_ENCRYPT.equalsIgnoreCase(operation) || OP_DECRYPT.equalsIgnoreCase(operation))){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
            if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
            if(output == null){throw new MissingArgumentException("Argument \"-out\" is mandatory for ChaCha20 operation "+operation);}
            SymmetricCiphersController.runChaCha20(operation, ALG_CHACHA20_POLY1305, key, input, output);
        }else {
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for ChaCha20");
        }
    }
    
    private static void startXChaCha20(String operation, String key, String input, String output) throws Exception{
        if(OP_KEYGEN.equalsIgnoreCase(operation)){
            SymmetricCiphersController.runChaCha20(ALG_XCHACHA20);
        }else if((OP_ENCRYPT.equalsIgnoreCase(operation) || OP_DECRYPT.equalsIgnoreCase(operation))){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
            if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
            if(output == null){throw new MissingArgumentException("Argument \"-out\" is mandatory for ChaCha20 operation "+operation);}
            SymmetricCiphersController.runChaCha20(operation, ALG_XCHACHA20, key, input, output);
        }else {
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for ChaCha20");
        }
    }
    
    /*HASH FUNCTION METHODS*/
    
    private static void startMD5(String operation, String input, String hash) throws UnsupportedOperationException, FileNotFoundException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runMd5(input);
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runMd5(input, hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for MD5");
        }
    }
    
    private static void startSHA_1(String operation, String input, String hash) throws UnsupportedOperationException, FileNotFoundException, NoSuchAlgorithmException, IOException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runSHA_1(input);
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runSHA_1(input, hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for SHA-1");
        }
    }
    
    private static void startSHA_2(String operation, String input, String mode, String hash) throws UnsupportedOperationException, FileNotFoundException, NoSuchAlgorithmException, IOException, MissingArgumentException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(mode==null || (!mode.equals("256") && !mode.equals("512"))) {throw new MissingArgumentException("Argument \"-mode\" must be 256 or 512");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runSHA_2(input, Integer.parseInt(mode));
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runSHA_2(input, Integer.parseInt(mode), hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for SHA-2");
        }
    }
    
    private static void startSHA_3(String operation, String input, String mode, String hash) throws FileNotFoundException, MissingArgumentException, IOException, NoSuchAlgorithmException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(mode==null || (!mode.equals("256") && !mode.equals("512"))) {throw new MissingArgumentException("Argument \"-mode\" must be 256 or 512");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runSHA_3(input, Integer.parseInt(mode));
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runSHA_3(input, Integer.parseInt(mode), hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for SHA-3");
        }
    }
    
    private static void startRIPEMD_160(String operation, String input, String hash) throws FileNotFoundException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runRIPEMD_160(input);
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runRIPEMD_160(input, hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for RIPEMD-160");
        }
    }
    
    private static void startWHIRPOOL(String operation, String input, String hash) throws FileNotFoundException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if(OP_RESUME.equalsIgnoreCase(operation)){
            HashesController.runWHIRPOOL(input);
        }else if(OP_VERIFY.equalsIgnoreCase(operation)){
            if(hash==null || !Files.exists(Paths.get(hash))) {throw new FileNotFoundException("Hash file \""+hash+"\" could not be found. Option \"-hash\" is mandatory for this operation.");}
            HashesController.runWHIRPOOL(input, hash);
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for RIPEMD-160");
        }
    }
}

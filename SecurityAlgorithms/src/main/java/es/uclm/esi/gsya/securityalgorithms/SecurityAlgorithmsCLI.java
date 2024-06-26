/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.uclm.esi.gsya.securityalgorithms;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.*;

/**
 *
 * @author Eugenio
 */
public class SecurityAlgorithmsCLI { 
    
    public static void main(String[] args) {
        // Define las opciones de línea de comandos
        Options options = new Options();

        options.addOption("alg", "algorithm", true, "Algoritmo a usar (AES, Camellia, ChaCha20, etc.)");
        options.addOption("op", "operation", true, "Tipo de operación: encrypt, decrypt, keygen");
        options.addOption("mode", true, "Modo de operación (para cifrados de bloque)");
        options.addOption("pad", "padding", true, "Padding scheme (para cifrados de bloque)");
        options.addOption("key", true, "Ruta a la clave o tamaño de clave (para generación de claves)");
        options.addOption("hash", true, "Ruta al fichero donde se encuentra el hash");
        options.addOption("in","input", true, "Ruta al archivo de entrada");
        options.addOption("out","output", true, "Ruta al archivo de salida");
        options.addOption("times", true, "Numero de veces que se repetirá la operación (solo encrypt o decrypt)");

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
            String times = cmd.getOptionValue("times");
            

            // Aquí puedes llamar a métodos específicos según la operación
            if ("AES".equalsIgnoreCase(algorithm)) {
                //Realizamos las operaciones con AES
                if("keygen".equalsIgnoreCase(operation)){
                    if(keyPath != null && keyPath.matches("^[1-9]\\d*$")){
                        SymmetricCiphersController.runAes(Integer.parseInt(keyPath));
                    }
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    SymmetricCiphersController.runAes(operation, mode, padding, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        SymmetricCiphersController.runAes(operation, mode, padding, keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else if ("Camellia".equalsIgnoreCase(algorithm)) {
                // Realizamos las operaciones con Camellia
                if("keygen".equalsIgnoreCase(operation)){
                    if(keyPath != null && keyPath.matches("^[1-9]\\d*$")){
                        SymmetricCiphersController.runCamellia(Integer.parseInt(keyPath));
                    }
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    SymmetricCiphersController.runCamellia(operation, mode, padding, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        SymmetricCiphersController.runCamellia(operation, mode, padding, keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else if ("ChaCha20".equalsIgnoreCase(algorithm)) {
                // Realizamos las operaciones con ChaCha20
                if("keygen".equalsIgnoreCase(operation)){
                    SymmetricCiphersController.runChaCha20();
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    if(mode != null)
                        SymmetricCiphersController.runChaCha20(operation, algorithm+"-"+mode, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        if(mode != null)
                            SymmetricCiphersController.runChaCha20(operation, algorithm+"-"+mode, keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else if("md5".equalsIgnoreCase(algorithm)){
                if("resume".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath);
                }else if("verify".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath, hashPath);
                }
            }else if("sha1".equalsIgnoreCase(algorithm)){
                try {
                    startSHA_1(operation, inputPath, keyPath);
                } catch (UnsupportedOperationException | FileNotFoundException ex) {
                    Logger.getLogger(SecurityAlgorithmsCLI.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex.getMessage());
                }
            }else if("sha2".equalsIgnoreCase(algorithm)){
                if("resume".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath);
                }else if("verify".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath, hashPath);
                }
            }else if("sha3".equalsIgnoreCase(algorithm)){
                if("resume".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath);
                }else if("verify".equalsIgnoreCase(operation)){
                    HashesController.runMd5(inputPath, hashPath);
                }
            }else {
                formatter.printHelp("SecurityAlgorithms", options);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("SecurityAlgorithms", options);
            System.exit(1);
        }
    }
    
    private static void startSHA_1(String operation, String input, String key) throws UnsupportedOperationException, FileNotFoundException{
        if(input==null || !Files.exists(Paths.get(input))) {throw new FileNotFoundException("Input file \""+input+"\" could not be found");}
        if("resume".equalsIgnoreCase(operation)){
            
        }else if("verify".equalsIgnoreCase(operation)){
            if(key==null || !Files.exists(Paths.get(key))) {throw new FileNotFoundException("Key file \""+key+"\" could not be found");}
        }else{
            throw new UnsupportedOperationException("Operation \""+operation+"\" not defined for SHA-1");
        }
    }
    
}

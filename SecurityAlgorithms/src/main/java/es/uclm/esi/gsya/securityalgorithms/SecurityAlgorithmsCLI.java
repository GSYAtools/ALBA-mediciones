/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package es.uclm.esi.gsya.securityalgorithms;

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
            String inputPath = cmd.getOptionValue("input");
            String outputPath = cmd.getOptionValue("output");
            String times = cmd.getOptionValue("times");
            

            // Aquí puedes llamar a métodos específicos según la operación
            if ("AES".equalsIgnoreCase(algorithm)) {
                //Realizamos las operaciones con AES
                if("keygen".equalsIgnoreCase(operation)){
                    if(keyPath != null && keyPath.matches("^[1-9]\\d*$")){
                        CiphersController.runAes(Integer.parseInt(keyPath));
                    }
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    CiphersController.runAes(operation, mode, padding, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        CiphersController.runAes(operation, mode, padding, keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else if ("Camellia".equalsIgnoreCase(algorithm)) {
                // Realizamos las operaciones con Camellia
                if("keygen".equalsIgnoreCase(operation)){
                    if(keyPath != null && keyPath.matches("^[1-9]\\d*$")){
                        CiphersController.runCamellia(Integer.parseInt(keyPath));
                    }
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    CiphersController.runCamellia(operation, mode, padding, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        CiphersController.runCamellia(operation, mode, padding, keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else if ("ChaCha20".equalsIgnoreCase(algorithm)) {
                // Realizamos las operaciones con ChaCha20
                if("keygen".equalsIgnoreCase(operation)){
                    CiphersController.runChaCha20();
                }else if(("encrypt".equalsIgnoreCase(operation) || "decrypt".equalsIgnoreCase(operation)) && !cmd.hasOption("times")){
                    if(mode == null)
                        CiphersController.runChaCha20(operation, algorithm, keyPath, inputPath, outputPath);
                    else
                        CiphersController.runChaCha20(operation, algorithm+"-"+mode, keyPath, inputPath, outputPath);
                }else {
                    if(times != null && times.matches("^[1-9]\\d*$")){
                        if(mode == null)
                            CiphersController.runChaCha20(operation, "ChaCha20-"+mode, keyPath, inputPath, outputPath, Integer.parseInt(times));
                        else
                            CiphersController.runChaCha20(operation, "ChaCha20", keyPath, inputPath, outputPath, Integer.parseInt(times));
                    }
                }
            } else {
                formatter.printHelp("SecurityAlgorithms", options);
            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("SecurityAlgorithms", options);
            System.exit(1);
        }
    }
    
}

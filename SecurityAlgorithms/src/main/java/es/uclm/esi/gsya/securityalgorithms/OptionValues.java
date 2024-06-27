/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.uclm.esi.gsya.securityalgorithms;

/**
 *
 * @author alarcos
 */
public interface OptionValues {
    public static String OP_KEYGEN = "keygen";
    public static String OP_ENCRYPT = "encrypt";
    public static String OP_DECRYPT= "decrypt";
    public static String OP_RESUME = "resume";
    public static String OP_VERIFY = "verify";
    
    public static String ALG_AES = "AES";
    public static String ALG_CAMELLIA = "Camellia";
    public static String ALG_CHACHA20 = "ChaCha20";
    public static String ALG_MD5 = "md5";
    public static String ALG_SHA1 = "sha-1";
    public static String ALG_SHA2 = "sha-2";
    public static String ALG_SHA3 = "sha-3";
}
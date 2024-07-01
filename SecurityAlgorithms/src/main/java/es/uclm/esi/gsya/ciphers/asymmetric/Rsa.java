/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.uclm.esi.gsya.ciphers.asymmetric;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 * Clase que maneja operaciones de cifrado RSA para generar pares de claves,
 * cifrar y descifrar archivos a nivel de byte.
 * 
 * @author Eugenio
 */
public class Rsa {
    private int rsaKeySize; // Tamaño de la clave RSA en bits

    /**
     * Constructor que inicializa la clase con un tamaño específico para las claves RSA.
     *
     * @param rsaKeySize Tamaño de la clave RSA en bits
     */
    public Rsa(int rsaKeySize) {
        this.rsaKeySize = rsaKeySize;
    }

    /**
     * Genera un par de claves RSA (pública y privada) con el tamaño especificado.
     *
     * @return KeyPair que contiene la clave pública y privada generada
     * @throws NoSuchAlgorithmException si el algoritmo RSA no está disponible
     */
    public KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(rsaKeySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Cifra datos utilizando la clave pública RSA.
     *
     * @param data      Datos a cifrar
     * @param publicKey Clave pública RSA
     * @return Datos cifrados
     * @throws Exception si ocurre un error durante el cifrado
     */
    public byte[] encryptData(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * Descifra datos utilizando la clave privada RSA.
     *
     * @param encryptedData Datos cifrados
     * @param privateKey    Clave privada RSA
     * @return Datos descifrados
     * @throws Exception si ocurre un error durante el descifrado
     */
    public byte[] decryptData(byte[] encryptedData, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(encryptedData);
    }

    /**
     * Guarda un par de claves RSA en archivos.
     *
     * @param publicKey  Clave pública RSA a guardar
     * @param privateKey Clave privada RSA a guardar
     * @param publicKeyFile  Nombre del archivo donde se guardará la clave pública
     * @param privateKeyFile Nombre del archivo donde se guardará la clave privada
     * @throws IOException si ocurre un error de entrada/salida al guardar las claves
     */
    public void saveKeys(Key publicKey, Key privateKey, String publicKeyFile, String privateKeyFile) throws IOException {
        try (ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
             ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile))) {
            publicKeyOS.writeObject(publicKey);
            privateKeyOS.writeObject(privateKey);
        }
    }

    /**
     * Lee un par de claves RSA desde archivos.
     *
     * @param publicKeyFile  Nombre del archivo que contiene la clave pública
     * @param privateKeyFile Nombre del archivo que contiene la clave privada
     * @return KeyPair que contiene la clave pública y privada leída desde los archivos
     * @throws IOException            si ocurre un error de entrada/salida al leer las claves
     * @throws ClassNotFoundException si la clase de objeto leído no se encuentra
     */
    public KeyPair readKeys(String publicKeyFile, String privateKeyFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream publicKeyIS = new ObjectInputStream(new FileInputStream(publicKeyFile));
             ObjectInputStream privateKeyIS = new ObjectInputStream(new FileInputStream(privateKeyFile))) {
            PublicKey publicKey = (PublicKey) publicKeyIS.readObject();
            PrivateKey privateKey = (PrivateKey) privateKeyIS.readObject();
            return new KeyPair(publicKey, privateKey);
        }
    }

    /**
     * Guarda datos en un archivo.
     *
     * @param data     Datos a guardar
     * @param fileName Nombre del archivo
     * @throws IOException si ocurre un error de entrada/salida al guardar el archivo
     */
    public void saveToFile(byte[] data, String fileName) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(data);
        }
    }

    /**
     * Lee datos desde un archivo.
     *
     * @param fileName Nombre del archivo a leer
     * @return Datos leídos desde el archivo
     * @throws IOException si ocurre un error de entrada/salida al leer el archivo
     */
    public byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] fileData = new byte[(int) file.length()];
        try (FileInputStream fis = new FileInputStream(file)) {
            fis.read(fileData);
        }
        return fileData;
    }
}

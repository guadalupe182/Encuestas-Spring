/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spring.encuestas.Library;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static org.apache.tomcat.util.codec.binary.Base64.decodeBase64;
import static org.apache.tomcat.util.codec.binary.Base64.encodeBase64;

/**
 *
 * @author adrian
 */
public class Encrypt {

    //Defining algorithm to be used (AES,DES,RSA)
    private final static String algorithm = "AES";
    //Defining encryption method
    private final static String encryption = "AES/CBC/pkcs5padding";
    private static String key = "92AE31A79FEEB2A3";
    private static String initializationVector = "0123456789ABCDEF";

    //password encryption
    public static String encrypt(String cleartext) throws Exception {
        //this class provides the functionality of an encryption
        //cryptographic for encryption and decryption
        Cipher cipher = Cipher.getInstance(encryption);
        //this class specifies a secret key in a vendor-independent manner
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        //this class specifies an initialization vector
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector.getBytes());
        //initializing encryption
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
        //encrypting password
        byte[] encrypted = cipher.doFinal(cleartext.getBytes());
        return new String(encodeBase64(encrypted));
    }
    
    //password decryption
    public static String decrypt(String encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(encryption);
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector.getBytes());
        byte [] enc = decodeBase64(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);
        byte[] decrypted = cipher.doFinal(enc);
        return new String(decrypted);
    }
}

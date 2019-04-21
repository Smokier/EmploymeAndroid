package com.example.employme;

import android.util.Log;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cifrado {

    private static final String AES ="AES";

    public static SecretKeySpec generarKey() throws NoSuchAlgorithmException {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(128);

        SecretKey secretKey = keyGenerator.generateKey();
        byte[] bytesSecretKey = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytesSecretKey,AES);
        return secretKeySpec;
    }


    public static String encriptar (String mensaje,Key key)throws Exception{
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE,key);

        byte[] mensajeEncriptado = cipher.doFinal(mensaje.getBytes());
        Log.d("Cifrado",new String(mensajeEncriptado));


    return  new String(mensajeEncriptado);
    }

    public static String desEncriptar(String des,Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE,key);
        byte [] mensajeDesencriptado = cipher.doFinal(des.getBytes());

        Log.d("Descifrado",new String(mensajeDesencriptado));

        return new String(mensajeDesencriptado);
    }



}

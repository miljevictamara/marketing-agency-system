package com.bsep.marketingacency.service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AESConverter {
    public static String encryptToString(SecretKey skey, String plaintext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final int blockSize = cipher.getBlockSize();
            byte[] initVector = new byte[blockSize];
            (new SecureRandom()).nextBytes(initVector);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);
            byte[] encoded = plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            byte[] ciphertext = new byte[initVector.length + cipher.getOutputSize(encoded.length)];

            // Copy IV to ciphertext array
            System.arraycopy(initVector, 0, ciphertext, 0, initVector.length);

            // Perform encryption
            cipher.doFinal(encoded, 0, encoded.length, ciphertext, initVector.length);

            // Convert ciphertext to Base64 string
            return Base64.getEncoder().encodeToString(ciphertext);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | ShortBufferException |
                 BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.toString());
        }
    }

    public static String decryptFromString(SecretKey skey, String base64Ciphertext)
            throws BadPaddingException, IllegalBlockSizeException {
        try {
            // Decode Base64 string to byte array
            byte[] ciphertext = Base64.getDecoder().decode(base64Ciphertext);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            final int blockSize = cipher.getBlockSize();
            byte[] initVector = Arrays.copyOfRange(ciphertext, 0, blockSize);
            IvParameterSpec ivSpec = new IvParameterSpec(initVector);
            cipher.init(Cipher.DECRYPT_MODE, skey, ivSpec);

            byte[] plaintext = cipher.doFinal(ciphertext, blockSize, ciphertext.length - blockSize);
            return new String(plaintext, java.nio.charset.StandardCharsets.UTF_8);
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException | NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.toString());
        }
    }


        ///da li treba isti iv vektor kod oba???
}

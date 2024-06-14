package com.bsep.marketingacency.keystores;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

public class KeyStoreWriter {
    private KeyStore keyStore;

    public KeyStoreWriter() {
        try {
            keyStore =KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

//    public void loadKeyStore(String fileName, char[] password) {
//        try {
//            File keystoreFile = new File(fileName);
//            if (!keystoreFile.exists()) {
//                keyStore = KeyStore.getInstance("JKS");
//                keyStore.load(null, password);
//                try (FileOutputStream fos = new FileOutputStream(keystoreFile)) {
//                    keyStore.store(fos, password);
//                }
//            } else {
//                keyStore = KeyStore.getInstance("JKS");
//                keyStore.load(new FileInputStream(fileName), password);
//            }
//        } catch (NoSuchAlgorithmException | IOException | CertificateException | KeyStoreException e) {
//            e.printStackTrace();
//        }
//    }

    public void loadKeyStore(String fileName, char[] password) {
        try {
            if(fileName != null) {
                keyStore.load(new FileInputStream(fileName), password);
            } else {
                keyStore.load(null, password);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void saveKeyStore(String fileName, char[] password) {
        try {
            keyStore.store(new FileOutputStream(fileName), password);
        } catch (KeyStoreException | NoSuchAlgorithmException |
                 CertificateException |
                 IOException e) {
            e.printStackTrace();
        }
    }

    public void writeSecretKey(String alias, SecretKey secretKey, char[] password) {
        try {
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            KeyStore.ProtectionParameter entryPassword =
                    new KeyStore.PasswordProtection(password);


            // Postavite tajni ključ u keystore
            keyStore.setEntry(alias, secretKeyEntry, entryPassword);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public static SecretKey generateSecretKey(String algorithm, int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(keySize);
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}


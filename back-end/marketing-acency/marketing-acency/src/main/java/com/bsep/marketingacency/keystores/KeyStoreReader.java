package com.bsep.marketingacency.keystores;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import javax.crypto.SecretKey;

public class KeyStoreReader {
    private KeyStore keyStore;

    public KeyStoreReader() {
        try {
            keyStore =KeyStore.getInstance("JCEKS");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }

    public SecretKey readSecretKey(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            KeyStore.ProtectionParameter entryPassword =
                    new KeyStore.PasswordProtection(keyPass);

            KeyStore.SecretKeyEntry secretKeyEntry =
                    (KeyStore.SecretKeyEntry) keyStore.getEntry(alias, entryPassword);

            return secretKeyEntry.getSecretKey();
        } catch (FileNotFoundException e) {
            System.err.println("Key store file not found: " + keyStoreFile);
        } catch (KeyStoreException |
                 NoSuchAlgorithmException | CertificateException |
                 IOException | UnrecoverableEntryException e) {
            e.printStackTrace();
        }

        return null;
    }

    public KeyStore getKeyStore(String keyStoreName, String keyStorePassword) {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(keyStoreName));
            keyStore.load(inputStream, keyStorePassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException | NoSuchProviderException |
                 CertificateException |
                 IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void listAliases(String keyStoreFile, char[] password) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                System.out.println(aliases.nextElement());
            }
        } catch (KeyStoreException |
                 NoSuchAlgorithmException | CertificateException |
                 IOException e) {
            e.printStackTrace();
        }
    }
}


package com.example.bendezugutierrez_sensor_evaluacionfinal;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;

public class cryptoHelper {

    public static Signature getSignature(){
        try{
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            KeyStore.PrivateKeyEntry keyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("alias", null);
            PrivateKey privateKey = keyEntry.getPrivateKey();
            Certificate certificate = keyEntry.getCertificate();

            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            return signature;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

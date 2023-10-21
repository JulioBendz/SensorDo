package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensorHuellasDactilares extends AppCompatActivity {

    Button fingerprintButton;
    FingerprintManager fingerprintManager;
    FingerprintManager.CryptoObject cryptoObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_huellas_dactilares);

        fingerprintButton = findViewById(R.id.btnFingerPrint);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (fingerprintManager != null && fingerprintManager.isHardwareDetected()){
            cryptoObject = new FingerprintManager.CryptoObject(cryptoHelper.getSignature());
            final CancellationSignal cancellationSignal = new CancellationSignal();

            fingerprintButton.setOnClickListener(v -> {

                Toast.makeText(SensorHuellasDactilares.this, "Autenticar Con Huella Digital", Toast.LENGTH_SHORT).show();

                fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        Toast.makeText(SensorHuellasDactilares.this, "Autenticación exitosa", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SensorHuellasDactilares.this, SQLiteCRUDactivity.class);
                        startActivity(i);

                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();

                        Toast.makeText(SensorHuellasDactilares.this, "Autenticación fallida", Toast.LENGTH_SHORT).show();
                    }
                }, null);
            });
        }else {
            Toast.makeText(this, "Huella digital no compatible", Toast.LENGTH_SHORT).show();
            fingerprintButton.setEnabled(false);
        }
    }
}
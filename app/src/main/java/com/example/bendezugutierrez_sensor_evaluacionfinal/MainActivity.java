package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button SProximidad;
    Button SAcelerometro;

    Button SHuellasD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SProximidad = (Button) findViewById(R.id.btnSensorDeProximidad);
        SAcelerometro = (Button) findViewById(R.id.btnSensorAcelerometro);
        SHuellasD = (Button) findViewById(R.id.btnSensorDeHuellas);

        SProximidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SensorDeProximidad.class);
                startActivity(i);
            }
        });

        SAcelerometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SensorAcelerometro.class);
                startActivity(i);
            }
        });

        SHuellasD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SensorHuellasDactilares.class);
                startActivity(i);
            }
        });

    }
}
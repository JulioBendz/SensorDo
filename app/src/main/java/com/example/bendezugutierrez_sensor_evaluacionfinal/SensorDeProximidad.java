package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SensorDeProximidad extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor Sensor;

    TextView proximtyTV;

    SensorEventListener sensorEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_de_proximidad);


        proximtyTV = findViewById(R.id.proximityTextView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(Sensor==null) {
            proximtyTV.setText("No se enontró el sensor de proximidad en este dispositivo.");
            finish();
        }
        sensorEventListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if(sensorEvent.sensor.getType() == Sensor.TYPE_PROXIMITY){
                    float distance = sensorEvent.values[0];
                    proximtyTV.setText("Proximidad: " + distance + " cm");
                }
                if (sensorEvent.values[0]<Sensor.getMaximumRange()){
                    getWindow().getDecorView().setBackgroundColor(Color.BLACK);
                    proximtyTV.setTextColor(getResources().getColor(android.R.color.white));
                }else{
                    getWindow().getDecorView().setBackgroundColor(Color.CYAN);
                    proximtyTV.setTextColor(getResources().getColor(android.R.color.black));
                }
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }
    public void start(){
        sensorManager.registerListener(sensorEventListener,Sensor,2000*1000);
    }
    public void stop(){
        sensorManager.unregisterListener(sensorEventListener);
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();


    }
}
package com.example.bendezugutierrez_sensor_evaluacionfinal;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SensorAcelerometro extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor sensor;

    SensorEventListener sensorEventListener;
    int whip=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_acelerometro);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        TextView textViewDI = findViewById(R.id.textViewDI);

        sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(sensor==null)
            finish();

        sensorEventListener= new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x=sensorEvent.values[0];
                System.out.println("valor giro"+x);
                if(x<-5 && whip==0){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    sound2();
                    textViewDI.setText("Movimiento hacia la izquierda");
                }else if(x>5 && whip==1){
                    whip++;
                    getWindow().getDecorView().setBackgroundColor(Color.RED);
                    textViewDI.setText("Movimiento hacia la derecha");
                }

                if (whip==2){
                    sound();
                    whip=0;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        start();
    }

    private void sound(){
        MediaPlayer mediaPlayer=MediaPlayer.create(this,R.raw.pato_de_goma_cut);
        mediaPlayer.start();
    }
    private void sound2(){
        MediaPlayer mediaPlayer2=MediaPlayer.create(this,R.raw.tono_mensaje);
        mediaPlayer2.start();
    }
    public void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
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
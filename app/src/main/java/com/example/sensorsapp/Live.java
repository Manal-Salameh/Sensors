package com.example.sensorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Live extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer,ambientTemperatureSensor, gyroSensor;

    TextView accx,accy,accz, ambienttemperature, gyrox, gyroy, gyroz;
    float lastX, lastY, lastZ;

    Button cancel;

    float deltaX = 0;
    float deltaY = 0;
    float deltaZ = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);


        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if(accelerometer!=null){
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("debug", "Registered accelerometer listener");
        }

        ambientTemperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(ambientTemperatureSensor!=null){
            sensorManager.registerListener(this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("debug", "Registred temperature listener");
        }

        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(gyroSensor!=null){
            sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("debug", "Registred gyro listener");
        }



        accx = findViewById(R.id.AccX);
        accy = findViewById(R.id.AccY);
        accz = findViewById(R.id.AccZ);
        gyrox = findViewById(R.id.GyroX);
        gyroy = findViewById(R.id.GyroY);
        gyroz = findViewById(R.id.GyroZ);
        ambienttemperature = findViewById(R.id.temp);


        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Live.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, ambientTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Sensor sensor = event.sensor;
        if(sensor.getType()== Sensor.TYPE_ACCELEROMETER){

            accx.setText("X: " + Float.toString(deltaX));
            accy.setText("Y: " +  Float.toString(deltaY));
            accz.setText("Z: "+ Float.toString(deltaZ));

            deltaX = Math.abs(lastX - event.values[0]);
            deltaY = Math.abs(lastY - event.values[1]);
            deltaZ = Math.abs(lastZ - event.values[2]);
        }

        else if(sensor.getType() == Sensor.TYPE_LIGHT){

            float ambientTemp = event.values[0];
            ambienttemperature.setText("Illuminance: " + Float.toString(ambientTemp)  +" lx");

        }
        else if(sensor.getType()==Sensor.TYPE_GYROSCOPE){

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            gyrox.setText("X: " + Float.toString(x)+ " rad/s");
            gyroy.setText("Y: "  + Float.toString(y) + " rad/s" );
            gyroz.setText("X: " + Float.toString(z) + " rad/s");

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

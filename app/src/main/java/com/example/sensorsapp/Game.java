package com.example.sensorsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

public class Game extends AppCompatActivity implements SensorEventListener {

    private ShapeDrawable ball = new ShapeDrawable();
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Animation animatedView = null;
    public static int x, y;
    public static int counter = 0;

    boolean GameEnd = false;

    public int displayWidth, displayHeight;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        counter = 0;
        animatedView = new Animation (this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayWidth = displayMetrics.widthPixels-100;
        displayHeight =  displayMetrics.heightPixels-100;


        x = displayWidth/2;
        y = displayHeight/2;

        Log.v("Y Size:" , Integer.toString(displayHeight));
        Log.v("X Size:" , Integer.toString(displayWidth));


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

        }
        setContentView(animatedView);
    }




    protected  void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected  void onPause(){
        super.onPause();
        sensorManager.unregisterListener( this);

    }


    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x-= (int) event.values[0];
            y += (int) event.values[1];

            if (x > displayWidth) {
                x = displayWidth;
                endGame();


            } else if (x < 0) {
                x = 0;
                endGame();

            }

            if (y > displayHeight-100) {

                y = displayHeight-100;
                endGame();
            } else if (y < 0) {
                y = 0;
                endGame();
            }

        }

    }


    public void onAccuracyChanged (Sensor sensor,int accuracy){

    }

    public void endGame(){

        GameEnd = true;
        Toast.makeText(getApplicationContext(), "Game over", Toast.LENGTH_SHORT).show();
        onPause();
        Intent intent = new Intent(Game.this, MainActivity.class);
        startActivity(intent);

    }



    public class Animation extends androidx.appcompat.widget.AppCompatImageView{

        static final int width = 100;
        static final int height = 100;

        public Animation(Context context) {

            super(context);
            ball = new ShapeDrawable(new OvalShape());
            ball.getPaint().setColor(Color.parseColor("#4b7bec"));

        }
        Paint p = new Paint();



        protected void onDraw(Canvas canvas) {


            p.setColor(Color.BLACK);
            p.setTextSize(80);


            ball.setBounds(x, y, x+ width, y+ height);
            canvas.drawColor(Color.parseColor("#ffffff"));
            ball.draw(canvas);
            invalidate();

        }

    }
}

package com.bdfoodcalorie.bdfoodcalorie;

/**
 * Created by ibrahim on 4/16/18.
 */


import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.itangqi.waveloadingview.WaveLoadingView;

public class FootStepMeasure extends Activity implements SensorEventListener, StepListener {
    private TextView textView;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private int numSteps;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private TextView TvSteps,TvCalorie,TvDistance;
    private Button BtnStart,BtnStop,BtnHome;

    // Wave loading view
    WaveLoadingView waveLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.footstep_screen);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        // Waveloading find
        waveLoadingView = findViewById(R.id.waveloadingId);
        waveLoadingView.setProgressValue(0);
        waveLoadingView.setBottomTitle("Goal : 100");
        waveLoadingView.setCenterTitle(String.format("0%%"));
        waveLoadingView.setTopTitle("Step: "+numSteps);


        /// Tv Measure result
        TvCalorie = findViewById(R.id.tv_calorieLoss);
        TvSteps =  findViewById(R.id.tv_steps);
        TvDistance = findViewById(R.id.tv_distance);
        BtnStart = findViewById(R.id.btn_start);
        BtnStop =  findViewById(R.id.btn_stop);
        BtnHome = findViewById(R.id.btn_backHome);


        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                numSteps = 0;
                sensorManager.registerListener(FootStepMeasure.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                TvCalorie.setText("0");
                TvSteps.setText("0");
                TvDistance.setText("0");
                sensorManager.unregisterListener(FootStepMeasure.this);

            }
        });

        BtnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FootStepMeasure.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    // update sensor
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        Double calLoss = numSteps*.05;
        Double distance = numSteps*2*0.3048;

        TvCalorie.setText(" "+CutPrecision(calLoss.toString()));
        TvSteps.setText(" "+numSteps);
        if(distance<1000) {
            TvDistance.setText(CutPrecision(distance.toString()) + "m");
        }else{
            distance/=1000;
            TvDistance.setText(CutPrecision(distance.toString()) + "km");
        }
        waveLoadingView.setProgressValue(numSteps*5);
        waveLoadingView.setBottomTitle("Goal : 20");
        if(numSteps>20){
            waveLoadingView.setTopTitle("Step: 20 full");
            waveLoadingView.setCenterTitle("100%");
        }else {
            waveLoadingView.setTopTitle("Step: "+numSteps);
            waveLoadingView.setCenterTitle(String.format("%d%%",(numSteps*5)));
        }

    }
    // set precision point 2
    String CutPrecision(String str){
        String tmp="";
        int c=0;
        boolean flag = false;
        for(int i = 0 ; i < str.length() ; i++){
            if(str.charAt(i)=='.'){
                flag =true;
            }
            if(flag){
                c++;
            }
            if(c> 2){
                return tmp;
            }
            tmp+=str.charAt(i);
        }
        return  tmp;

    }


}

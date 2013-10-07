package com.example.helloworld;

import java.util.List;
import com.soundcloud.api.*;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
	// Variables ----------------------------------------------
	private SensorManager	mSensorManager;
	private Sensor			mAccel;
	private TextView		mAccelNum;
	private ScrollView		mScrollView;
	private LinearLayout	mLinearLay;
	
	// Methods ------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        Log.i("ACACAC", String.format(">> Senors: %s", deviceSensors));
        Log.i("ACACAC", "CAN YOU READ ME?????");
        setUpSensors();
        beginAccelPrint();
        mScrollView	= (ScrollView) findViewById(R.id.scroll_thing);
        mLinearLay	= (LinearLayout) findViewById(R.id.linear_thing);
        TextView view = (TextView) mLinearLay.getChildAt(0);
        TextView clone;
        for (int i = 0; i < 10; i++) {
        	clone = new TextView(getApplicationContext());
        	clone.setLayoutParams(view.getLayoutParams());
        	clone.setText(String.format("%s : %d", view.getText(), i));
        	mLinearLay.addView(clone);
        }
        
    }
    
    private void beginAccelPrint() {
    	mAccelNum = (TextView) findViewById(R.id.accel_num);
    	mAccelNum.setText(mAccel.toString());
    }
    
    private void setUpSensors() {
    	mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    	if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
    		mAccel = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	} else {
    		Toast.makeText(getApplicationContext(), "NO ACCELLEROMETER :(", Toast.LENGTH_LONG);
    	}
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Sensor Methods ---------------------------------------------------------
	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		mAccelNum.setText(String.format("%d\n%f\n%f\n%f", arg0.values.length,
											arg0.values[0],
											arg0.values[1],
											arg0.values[2]));
		switch(getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_PORTRAIT:
			scrollView(arg0.values[1]);
			break;
		case Configuration.ORIENTATION_LANDSCAPE:
			scrollView(arg0.values[0]);
			break;
		default:
		}
	}

	private void scrollView(float f) {
		// TODO Auto-generated method stub
		float percent = Math.abs(f)/10;
		int place = (int) (mScrollView.getHeight() * percent);
		
		mScrollView.scrollTo(0, place);
	}
    
}

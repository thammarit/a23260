package com.egco428.a23260;


import android.app.ProgressDialog;
import android.os.Handler;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.Locale;

public class SignupActivity extends AppCompatActivity implements SensorEventListener {


    EditText username, password, latitude, longtitude;
    String getusername, getpassword, finalLatitude, finalLongtitude;

    String getlatitude, getlongtitude;

    Double rndlatitude, rndlongtitude;

    private long lastUpdate;
    private SensorManager sensorManager;

    private MapsDataSource datasource;

    boolean isInserted;

    Double empty;

    Double getFinalLatitude, getFinalLongtitude;

    Double getEmptyLatitude, getEmptyLongtitude;

    String storedUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        datasource = new MapsDataSource(this);
        datasource.open();

    }

    public void backtoLogin(View view) {
        Intent clearBackLogin = new Intent(SignupActivity.this, LoginActivity.class);
        clearBackLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(clearBackLogin);
    }

    public void addUser(View view) {

        username = (EditText) findViewById(R.id.username_puttext);
        getusername = username.getText().toString();
        password = (EditText) findViewById(R.id.password_puttext);
        getpassword = password.getText().toString();
        latitude = (EditText) findViewById(R.id.latitude_puttext);
        getlatitude = latitude.getText().toString();
        longtitude = (EditText) findViewById(R.id.longtitude_puttext);
        getlongtitude = longtitude.getText().toString();


        if (getlatitude.equals("") && getlongtitude.equals("")) {
            empty = 0.000000;
            getEmptyLatitude = empty;
            getEmptyLongtitude = empty;

            getFinalLatitude = getEmptyLatitude;
            getFinalLongtitude = getEmptyLongtitude;

            System.out.println("Empty : " + getFinalLatitude + "," + getFinalLongtitude);
        } else {
            getFinalLatitude = Double.parseDouble(latitude.getText().toString());
            getFinalLongtitude = Double.parseDouble(longtitude.getText().toString());
            System.out.println("Not Empty : " + getFinalLatitude + "," + getFinalLongtitude);
        }


        if (!getusername.equals("") && !getpassword.equals("") && !getFinalLatitude.equals(getEmptyLatitude) && !getFinalLongtitude.equals(getEmptyLongtitude)) {

            storedUsername = datasource.checkUsernameRecord(getusername);
            System.out.println("Username is :" + storedUsername);

            if (getusername.equals(storedUsername)) {
                Toast.makeText(getApplicationContext(), "This username is already used",
                        Toast.LENGTH_SHORT).show();
                username.setText("");
            } else {
                isInserted = datasource.createMap(getusername, getpassword, getFinalLatitude, getFinalLongtitude);

                if (isInserted) {
                    Toast.makeText(getApplicationContext(), "Data inserted",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Data not inserted!",
                            Toast.LENGTH_SHORT).show();
                }

                datasource.close();
                Intent clearBackLogin = new Intent(SignupActivity.this, LoginActivity.class);
                clearBackLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(clearBackLogin);
            }
        } else {
                Toast.makeText(getApplicationContext(), "Please complete the form!",
                        Toast.LENGTH_SHORT).show();
        }
    }


    public void randomClick(View view) {
        latitude = (EditText) findViewById(R.id.latitude_puttext);
        longtitude = (EditText) findViewById(R.id.longtitude_puttext);

        rndlatitude = getRandomCoordinate(-85.000000, 85.000000);
        rndlongtitude = getRandomCoordinate(-179.999989, 179.999989);

        finalLatitude = String.format(Locale.ENGLISH, "%.6f", rndlatitude);
        finalLongtitude = String.format(Locale.ENGLISH, "%.6f", rndlongtitude);

        latitude.setText(finalLatitude);
        longtitude.setText(finalLongtitude);
    }

    public double getRandomCoordinate(Double from, Double to) {
        return Math.random() * ((from) - (to)) - from;
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            getAccelerometer(event);
        }
    }

    private void getAccelerometer(SensorEvent event) {
        float[] values = event.values;

        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();

        if (accelationSquareRoot > 3) {
            if (actualTime - lastUpdate < 600) {
                return;
            }
            lastUpdate = actualTime;
            randomShake();
        }
    }

    public void randomShake(){
        latitude = (EditText) findViewById(R.id.latitude_puttext);
        longtitude = (EditText) findViewById(R.id.longtitude_puttext);

        rndlatitude = getRandomCoordinate(-85.000000, 85.000000);
        rndlongtitude = getRandomCoordinate(-179.999989, 179.999989);

        finalLatitude = String.format(Locale.ENGLISH, "%.6f", rndlatitude);
        finalLongtitude = String.format(Locale.ENGLISH, "%.6f", rndlongtitude);

        latitude.setText(finalLatitude);
        longtitude.setText(finalLongtitude);
    }

    @Override
    public void onAccuracyChanged (Sensor sensor,int accuracy){

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener
                (this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        //will not get value from phone
    }
}




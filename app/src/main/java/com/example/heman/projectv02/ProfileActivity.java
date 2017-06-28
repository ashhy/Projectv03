package com.example.heman.projectv02;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    //Declare
    private static final int MY_PERMISSIONS_REQUEST_CODE = 1;
    private TextView textViewUsername, textViewUserEmail;
    private Button buttonGPS;
    private boolean gpsEnabled;
    Context context;
   // private LocationManager locationManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Initial Check
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        if (checkPermissions()){
            Toast.makeText(this, "You have Permission", Toast.LENGTH_LONG).show();
        }else {
            setPermissions();
        }
        isLocationServiceEnabled();
        if (gpsEnabled){
            Toast.makeText(this, "GPS Enabled", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "GPS not Enabled", Toast.LENGTH_LONG).show();
        }


        //Initialize
        buttonGPS = (Button) findViewById(R.id.buttonGPS);
        buttonGPS.setOnClickListener(this);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        gpsEnabled = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this,"This will open up the settings", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == buttonGPS) {
            startActivity(new Intent(getApplicationContext(), SurveyListActivity.class));
        }
    }


    //Permissions
    protected boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }
    protected void setPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSIONS_REQUEST_CODE){
            return;
        }
        boolean isGranted = true;
        for (int result : grantResults){
            if (result != PackageManager.PERMISSION_GRANTED){
                isGranted = false;
                break;
            }
        }
        if (isGranted){
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
        }
    }


    //Location Enabled
    public void isLocationServiceEnabled() {
        try {
            LocationManager locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){}
    }
}

package com.example.heman.projectv02;

import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class FinalPage extends AppCompatActivity implements View.OnClickListener, LocationListener {

    //Declare

    private Button buttonSubmit;
    private TextView tvLat, tvLong;
    public double latitude,longitude;
    boolean locationAvailable;
    boolean locationRequired;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationCallback locationCallback;
    Location lastLocation;
    private final int PERMISSION_LIST_REQUEST_CODE=123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_page);

        locationAvailable = false;
        locationRequired = true;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback=new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                lastLocation=locationResult.getLastLocation();
                for(Location location:locationResult.getLocations()){
                    latitude=location.getLatitude();
                    longitude=location.getLongitude();
          //          Toast.makeText(FinalPage.this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
          //          Toast.makeText(FinalPage.this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
                }

                latitude = lastLocation.getLatitude();
                longitude = lastLocation.getLongitude();

            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setAndCheckPermission();
        }


        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(this);
    }



    public void setAndCheckPermission(){
        int finelocation= ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int coarselocation=ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        List<String> plist=new ArrayList<String>();
        if(finelocation!=PackageManager.PERMISSION_GRANTED)plist.add(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if(coarselocation!=PackageManager.PERMISSION_GRANTED)plist.add(android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if(!plist.isEmpty()){
            ActivityCompat.requestPermissions(this,plist.toArray
                    (new String[plist.size()]),PERMISSION_LIST_REQUEST_CODE);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        LocationRequest locationRequest=setLocation();
        setLocationSettings(locationRequest);
        if (ActivityCompat.checkSelfPermission(FinalPage.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(FinalPage.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Unable to receive location permission", Toast.LENGTH_SHORT).show();
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,  locationCallback, null);
    }


    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private LocationRequest setLocation(){
        LocationRequest locationRequest=new LocationRequest();
        locationRequest.setInterval(500);
        locationRequest.setFastestInterval(10);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return locationRequest;
    }


    private void setLocationSettings(final LocationRequest locationRequest){
        LocationSettingsRequest.Builder builder=new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient= LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task=settingsClient.checkLocationSettings(builder.build());
        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                locationAvailable=true;
                Log.d("INSIDE SETTINGS",String.valueOf(locationAvailable));
                Log.d("Location Available",String.valueOf(locationAvailable));
                Log.d("LOCATION REQUIRED",String.valueOf(locationRequired));

            }
        });
        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(FinalPage.this,
                                    12);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(this, String.valueOf(latitude), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, String.valueOf(longitude), Toast.LENGTH_SHORT).show();
    }
}

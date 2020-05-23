package com.example.sensorsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class GPS extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationProvider;


    TextView latText, longText, distanceText;
    LocationCallback locationCallback;
    LocationRequest locationRequest;
    double longitude, latitude;
    Location location;
    Location oldLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }


        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);

        latText = findViewById(R.id.Lati);
        longText = findViewById(R.id.Long);
        distanceText = findViewById(R.id.Dist);

        locationRequest = new LocationRequest().create();
        locationRequest.setInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        getlastLoc();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    longitude = location.getLongitude();
                    latitude = location.getLatitude();

                    latText.setText("Latitude:\n " + Double.toString(latitude));
                    longText.setText("Longitude: " + Double.toString(longitude));

                    double distance = distanceCalculator(oldLocation, location);
                    distanceText.setText(String.format("%.2f", distance) + " meters");
                    if(distance>50){
                        distanceText.setText("YOU WENT TOO FAR");
                    }

                }
            }
        };


        mFusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void getlastLoc() {

        mFusedLocationProvider.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                location.setLatitude(latitude);
                location.setLongitude(longitude);
                oldLocation = location;
            }
        });
    }

    public double distanceCalculator(Location location1, Location location2){

        if(location1!=null&& location2!=null){

            double distance = location1.distanceTo(location2);
            return distance;
        }else return 0.0;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}

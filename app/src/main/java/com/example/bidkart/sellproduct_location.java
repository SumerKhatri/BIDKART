package com.example.bidkart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class sellproduct_location extends AppCompatActivity {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1 ;
    private Button btn;
    private TextView textLatLong,textAdress;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellproduct_location);


        btn = findViewById(R.id.buttoGetCurrentLocation);
        textLatLong = findViewById(R.id.textLatLon);
        progressBar = findViewById(R.id.progressBar);
        textAdress = findViewById(R.id.textAdress);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(sellproduct_location.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSION);

                }else {
                        getCurrentLocation();
                }



            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }else{
                Toast.makeText(this,"Permisiion not granted",Toast.LENGTH_LONG).show();
            }
        }


    }

    private void getCurrentLocation(){

        progressBar.setVisibility(View.VISIBLE);

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(sellproduct_location.this).requestLocationUpdates(locationRequest,
                new LocationCallback(){

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        LocationServices.getFusedLocationProviderClient(sellproduct_location.this).removeLocationUpdates(this);

                        if(locationResult != null && locationResult.getLocations().size()>0){
                            int latestLocationIndex = locationResult.getLocations().size()-1;

                            double latitude=
                                    locationResult.getLocations().get(latestLocationIndex).getLatitude();
                            double longitude=
                                    locationResult.getLocations().get(latestLocationIndex).getLongitude();
                            textLatLong.setText(
                                    String.format("Latitude: %s\n longitude: %s ",latitude,longitude)
                            );

                            Geocoder geocoder = new Geocoder(sellproduct_location.this, Locale.getDefault());
                            List<Address> addresses = null;
                            try {
                                addresses = geocoder.getFromLocation(latitude, longitude, 1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String cityName = addresses.get(0).getLocality();
                            String stateName = addresses.get(0).getAddressLine(0);
                            String countryName = addresses.get(0).getCountryName();

                            textAdress.setText(String.format("City: %s \nFull address:  %s \n Country: %s  ",cityName,stateName,countryName));

                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                }, Looper.getMainLooper());

    }


}

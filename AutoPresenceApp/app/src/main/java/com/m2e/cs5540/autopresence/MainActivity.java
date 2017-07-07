package com.m2e.cs5540.autopresence;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.m2e.cs5540.autopresence.location.AppLocationListener;

public class MainActivity extends AppCompatActivity {
   private AppLocationListener locationListener;
   private TextView textView;

   @Override protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      this.textView = (TextView) findViewById(R.id.textView);
      locationListener = new AppLocationListener(textView);

      LocationManager locationManager = (LocationManager) this.getSystemService(
            Context.LOCATION_SERVICE);
      if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this,
                  Manifest.permission.ACCESS_COARSE_LOCATION) ==
                  PackageManager.PERMISSION_GRANTED) {
         locationManager.requestLocationUpdates(
               LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
               0, locationListener);
      } else {
         requestPermissions(
               new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                     Manifest.permission.ACCESS_FINE_LOCATION}, 1220);
         locationManager.requestLocationUpdates(
               LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
               0, locationListener);
         textView.setText("You do not have permission to access location " +
               "co-ordinates!");
      }
   }
}

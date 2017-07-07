package com.m2e.cs5540.autopresence.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by maeswara on 7/6/2017.
 */
public class AppLocationListener implements LocationListener {
    private TextView textView;

    public AppLocationListener(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onLocationChanged(Location location) {
        textView.setText("Your location: latitude: " + location.getLatitude()
                + " longitude: " + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

package com.aidanssite.traincatch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager mLocationManager;
    private final int LOCATION_REFRESH_TIME = 60 * 1000;
    private final int LOCATION_REFRESH_DISTANCE = 0;
    Location userLoc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        try {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        } catch (SecurityException | NullPointerException e) {
            Log.e("Exception", "Location error:" + e);
        }

        userLoc = getUserLoc();
        Log.d("Location Info", "Result: " + userLoc);

        if (userLoc != null) {
            String[][] latLonParam = {{"lat", Double.toString(userLoc.getLatitude())}, {"lon", Double.toString(userLoc.getLongitude())}, {"radius", "100"}, {"type", "rail_stations"}};
            APIManager septaLocAPI = new APIManager(APIManager.SEPTA, APIManager.SYSTEM_LOCATIONS, latLonParam);
            septaLocAPI.execute();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (userLoc != null) {
            LatLng userLatLng = new LatLng(userLoc.getLatitude(), userLoc.getLongitude());
            mMap.addMarker(new MarkerOptions().position(userLatLng).title("You are here"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15.0f));
        }
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

    private Location getUserLoc() {
        Location lastLoc = null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(LocationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        } else {
            lastLoc = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        return lastLoc;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(LocationActivity.this, "Permission denied to utilize your fine location. Location services may be inaccurate .", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            Log.d("Location Change", "Location:" + location);
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
    };
}

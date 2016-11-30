package com.egco428.a23260;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    Double retrieveLatitude, retrieveLongtitude;

    String retrieveUsername;

    String locationText;

    TextView retUsername;

    ImageButton backbutton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle pass = getIntent().getExtras();

        retrieveLatitude = pass.getDouble("LATITUDE");
        retrieveLongtitude = pass.getDouble("LONGTITUDE");
        retrieveUsername = pass.getString("USERNAME");

        locationText = getString(R.string.title_nameL);

        //System.out.println("Co-or retrieve : " + retrieveLatitude + "," + retrieveLongtitude);

        retUsername = (TextView) findViewById(R.id.title_toolbar3);
        retUsername.setText(retrieveUsername + "'s" + " " + locationText);

        backbutton2 = (ImageButton) findViewById(R.id.backButton2);
        backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocationActivity.super.onBackPressed();
            }
        });
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

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(retrieveLatitude, retrieveLongtitude);
        mMap.addMarker(new MarkerOptions().position(location).title(retrieveUsername +"'s" + " " + locationText));
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        CameraUpdate userLocation = CameraUpdateFactory.newLatLngZoom(location, 5);
        mMap.animateCamera(userLocation);
    }
}

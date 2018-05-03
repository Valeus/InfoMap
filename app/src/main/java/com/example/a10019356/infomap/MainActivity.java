package com.example.a10019356.infomap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    SupportMapFragment mapFragment;
    GoogleMap map;
    double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);
        mapFragment.getView().setClickable(true);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        final LatLng touch = new LatLng(lat, lng);
        Marker test = googleMap.addMarker(new MarkerOptions().position(touch).title("TEST").snippet("Population: 1"));
        test.showInfoWindow();
    }

}


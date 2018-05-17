package com.example.a10019356.infomap;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    MapView mapView;
    static final String MAPVIEW_KEY = "Kiddo";
    GoogleMap googleMap;
    int markerCount= 0;
    Marker test;
    String string;
    double latitude, longitude;
    String title = "MLT Banana";
    String status = "broken";
    GetInfo task;
    int exe=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_KEY);
        }
        mapView = findViewById(R.id.id_map);
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
        latitude = 30.0;
        longitude = 30.0;
        task = new GetInfo();
        task.execute();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latitude = latLng.latitude;
                longitude = latLng.longitude;
                Log.d("banana", latitude+" , "+longitude);
                task = new GetInfo();
                task.execute();
                if(status.equals("OK")){
                    Log.d("findError","OK");
                    task = new GetInfo();
                    task.execute();
                    if(title.contains("Unnamed Road")) {
                        title.replace("Unnamed Road, ", "");
                        Log.d("xd", "replacement thing works");
                    }
                    if (markerCount == 0) {
                        test = googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
                        test.showInfoWindow();
                        markerCount++;
                    }
                    if (markerCount == 1) {
                        test.remove();
                        test = googleMap.addMarker(new MarkerOptions().position(latLng).title(title));
                        test.showInfoWindow();
                    }
                }else if(status.equals("ZERO_RESULTS")) {
                    Log.d("findError", "zero result");
                    task = new GetInfo();
                    task.execute();
                    if (markerCount == 0) {
                        test = googleMap.addMarker(new MarkerOptions().position(latLng).title("Please pick a more interesting place"));
                        test.showInfoWindow();
                        markerCount++;
                    }
                    if (markerCount == 1) {
                        test.remove();
                        test = googleMap.addMarker(new MarkerOptions().position(latLng).title("Please pick a more interesting place"));
                        test.showInfoWindow();
                    }
                }else {
                    Log.d("broke", "how fix");
                }

            }
        });

    }

    public class GetInfo extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            URL gMaps;
            URLConnection urlConnection;
            InputStream inputStream;
            BufferedReader bufferedReader;
            String hold;
            if(exe<=1)
                exe++;
            try {
                gMaps = new URL("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + latitude + "," + longitude + "&key=AIzaSyD8h4VOwljg9KuxvoQ3-WrxcnFP4FDdS2k");
                urlConnection = gMaps.openConnection();
                inputStream = urlConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                hold = bufferedReader.readLine();
                string = hold;
                while (hold != null) {
                    hold = bufferedReader.readLine();
                    string += hold;
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject yada = null;
            try {
                yada = new JSONObject(string);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                status = yada.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray blah = null;
            try {
                blah = yada.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject funnn = null;
            try {
                funnn = blah.getJSONObject(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if(status.equals("OK"))
                    title = funnn.getString("formatted_address");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
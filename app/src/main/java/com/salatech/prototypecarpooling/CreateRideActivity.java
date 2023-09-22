package com.salatech.prototypecarpooling;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.libraries.places.api.model.Place;

import java.util.Arrays;
import java.util.List;

public class CreateRideActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private MapView mapView;
    private Button selectStartLocationButton;
    private Button selectEndLocationButton;

    private static final int AUTOCOMPLETE_REQUEST_CODE_START = 1;
    private static final int AUTOCOMPLETE_REQUEST_CODE_END = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        mapView = findViewById(R.id.mapView2); // Initialize the MapView
        selectStartLocationButton = findViewById(R.id.buttonSelectStartLocation);
        selectEndLocationButton = findViewById(R.id.buttonSelectEndLocation);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "AIzaSyAgaakSSsGesaAYR6yKkwBNFz5Mjd82M0c");
        }

        if (mapView != null) {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        // Set onClick listeners for location selection buttons
        selectStartLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation(AUTOCOMPLETE_REQUEST_CODE_START);
            }
        });

        selectEndLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectLocation(AUTOCOMPLETE_REQUEST_CODE_END);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng mapPakistan = new LatLng(30.3753, 69.3451);
        this.googleMap.addMarker(new MarkerOptions().position(mapPakistan).title("Marker in Pakistan"));
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(mapPakistan));
    }

    // Handle the result from the Place Picker
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTOCOMPLETE_REQUEST_CODE_START || requestCode == AUTOCOMPLETE_REQUEST_CODE_END) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                // You can access the selected location's details here:
                String placeName = place.getName();
                LatLng placeLatLng = place.getLatLng();

                // Do something with the selected location, such as updating the UI
                if (requestCode == AUTOCOMPLETE_REQUEST_CODE_START) {
                    // This is the start location
                    // Update your UI accordingly
                } else {
                    // This is the end location
                    // Update your UI accordingly
                }
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Handle errors (e.g., no network, permission denied, API error)
            // You can add error handling here
        }
    }

    // Create a method to handle location selection
    private void selectLocation(int requestCode) {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);

        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(this);

        startActivityForResult(intent, requestCode);
    }

    // Make sure to add the necessary lifecycle methods for the MapView
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }
}

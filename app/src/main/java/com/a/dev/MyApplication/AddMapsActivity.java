package com.a.dev.MyApplication;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.a.dev.MyApplication.tools.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class AddMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;

    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapAdd);
        if (mapFragment != null) {
            mapFragment.onCreate(null);
            mapFragment.onResume();
            mapFragment.getMapAsync(this);
        }else {
            Toast.makeText(AddMapsActivity.this, "Gagal load Maps, Silahkan Coba Lagi...", Snackbar.LENGTH_LONG).show();
        }

        gpsTracker = new GPSTracker(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(AddMapsActivity.this);
        String lat = String.valueOf(gpsTracker.getLatitude());
        String longi = String.valueOf(gpsTracker.getLongitude());

        mMap = googleMap;

        Geocoder geocoder = new Geocoder(AddMapsActivity.this, Locale.getDefault());

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {
            List<Address> addresses = null; // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addresses = geocoder.getFromLocation(gpsTracker.getLatitude(), gpsTracker.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();

            LatLng lokasiku = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));

            googleMap.addMarker(new MarkerOptions().position(lokasiku).title("Ini Lokasi saya").snippet(address + ", " + city + ", " + state + ", " + country)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pointuser));
            CameraPosition myLocation = CameraPosition.builder().target(new LatLng(Double.parseDouble(lat), Double.parseDouble(longi))).zoom(16).bearing(0).tilt(45).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(myLocation));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(AddMapsActivity.this, "Jaringan Internet Tidak Stabil, Silahkan Coba Lagi...", Snackbar.LENGTH_LONG).show();
        }

    }
}

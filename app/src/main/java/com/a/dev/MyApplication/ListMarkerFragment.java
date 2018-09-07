package com.a.dev.MyApplication;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.a.dev.MyApplication.tools.GPSTracker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class ListMarkerFragment extends Fragment implements  OnMapReadyCallback {
    private MapView mMapView;
    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private MarkerOptions tempat;
    private ArrayList<HashMap<String, String>> arrayList;
    private FabSpeedDial fabSpeedDial;

    public ListMarkerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = view.findViewById(R.id.map);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }else {
            Snackbar.make(getView(), "Gagal load Map, Silahkan Coba Lagi...", Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_marker, null);
        gpsTracker = new GPSTracker(getActivity());
        fabSpeedDial = view.findViewById(R.id.fabCollapse);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_add:
                        Intent toAddLocation = new Intent(getActivity(), AddMapsActivity.class);

                        startActivity(toAddLocation);
                        Toast.makeText(getActivity(), "Add Clicked", Toast.LENGTH_LONG).show();
                        return true;
                    case R.id.action_current:
                        Toast.makeText(getActivity(), "Current Clicked", Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        String lat = String.valueOf(gpsTracker.getLatitude());
        String longi = String.valueOf(gpsTracker.getLongitude());

        mMap = googleMap;

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

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
            Snackbar.make(getView(), "Jaringan Internet Tidak Stabil, Silahkan Coba Lagi...", Snackbar.LENGTH_LONG).show();
        }

    }

}
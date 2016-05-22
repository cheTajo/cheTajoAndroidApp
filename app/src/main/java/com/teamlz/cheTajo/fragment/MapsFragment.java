package com.teamlz.cheTajo.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamlz.cheTajo.R;
import com.teamlz.cheTajo.object.HairDresser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Firebase myFirebase;
    private Firebase hairDressersFirebase;
    private List<HairDresser> hairDresserList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        hairDresserList=new ArrayList<>();
        // inflat and return the layout
        View v = inflater.inflate(R.layout.fragment_maps, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap gMap) {
                googleMap = gMap;
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                final View locationButton = ((View) mMapView.findViewById(new Integer(1)).getParent()).findViewById(new Integer(2));
                locationButton.setVisibility(View.INVISIBLE);
                afterMapLoad();

                HomeFragment.fab_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationButton.performClick();
                    }
                });
            }
        });

        // Perform any camera updates here
        return v;
    }

    private void setMarker(HairDresser hd){

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(hd.getLatitude(), hd.getLongitude())).title(hd.getShopName());

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));

        // adding marker
        googleMap.addMarker(marker);
    }

    private void afterMapLoad() {

        //add marker for all hairdressers
        myFirebase= new Firebase(getString(R.string.firebase_url));
        hairDressersFirebase=myFirebase.child("hairDressers");
        hairDressersFirebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HairDresser hd = snapshot.getValue(HairDresser.class);
                    if(!hairDresserList.contains(hd)) hairDresserList.add(hd);
                }

                Iterator<HairDresser> i=hairDresserList.iterator();
                while(i.hasNext()){
                    setMarker(i.next());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        //Zoom camera
        /*CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));*/

    }

    public static MapsFragment newInstance() {

        Bundle args = new Bundle();

        MapsFragment fragment = new MapsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}
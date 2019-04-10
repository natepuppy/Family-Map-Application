package com.example.familymapapplication;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;


import java.util.HashMap;

import model.Event;
import model.Person;


public class MapFragment extends Fragment implements OnMarkerClickListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;


    private ImageView mPersonIcon;
    private TextView mName;
    private TextView mEventDetails;
    private Person mSendToPersonActivity;


    public static MapFragment newInstance(){
        return new MapFragment();
    }


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//
//        }
//    }











    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        setHasOptionsMenu(true);
        super.onCreateView(layoutInflater, viewGroup, bundle);
        View v = layoutInflater.inflate(R.layout.fragment_map, viewGroup, false);

        mPersonIcon = v.findViewById(R.id.person_icon);
        mName = v.findViewById(R.id.person_name_text);
        mEventDetails = v.findViewById(R.id.event_details_text);

        mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

//        setSupportActionBar(findViewById(R.id.my_toolbar))

        mPersonIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(new Intent(getActivity(), PersonActivity.class));
                i.putExtra("key", mSendToPersonActivity.getPersonID());
                startActivity(i);
                //startActivity(new Intent(getActivity(), PersonActivity.class));
            }
        });
        mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(new Intent(getActivity(), PersonActivity.class));
                i.putExtra("key", mSendToPersonActivity.getPersonID());
                startActivity(i);
                //startActivity(new Intent(getActivity(), PersonActivity.class));
            }
        });
        mEventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(new Intent(getActivity(), PersonActivity.class));
                i.putExtra("key", mSendToPersonActivity.getPersonID());
                startActivity(i);
                //startActivity(new Intent(getActivity(), PersonActivity.class));
            }
        });

        return v;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_map, container, false);
//
////        newButton = view.findViewById(R.id.Sign_In_Button);
////
////        newButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//
//        return view;
//    }


    @Override
    public void onResume()
    {
        super.onResume();
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
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        ModelData data = ModelData.getInstance( );

        HashMap<String, Float> eventTypes = new HashMap();
        float color = 10.0f;

        for (Event event : data.getEvents().values()) {
            if (!eventTypes.containsKey(event.getEventType())) {
                eventTypes.put(event.getEventType(), new Float(color));
                if (color < 360.0f) {
                    color += 30.0f;
                }
                else {
                    color += 20.0f;
                }
            }
        }

        for (Event event : data.getEvents().values()) {
            Marker newMarker = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLatitude(), event.getLongitude()))
                    .icon(BitmapDescriptorFactory.defaultMarker(eventTypes.get(event.getEventType())))
                    .title(event.getEventType()));
            newMarker.setTag(event.getEventID());

            if (event.getPersonID().equals(data.getPersonID())) {
                onMarkerClick(newMarker);
            }
        }
        mMap.setOnMarkerClickListener(this);
    }




    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.map_menu, menu);

        MenuItem searchButton = menu.findItem(R.id.search_item);
        MenuItem filterButton =  menu.findItem(R.id.filter_item);
        MenuItem settingsButton = menu.findItem(R.id.settings_item);

        Drawable searchIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_search).colorRes(R.color.color_accent).sizeDp(40);
        Drawable filterIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_filter).colorRes(R.color.color_accent).sizeDp(40);
        Drawable settingsIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_gear).colorRes(R.color.color_accent).sizeDp(40);

        searchButton.setIcon(searchIcon);
        filterButton.setIcon(filterIcon);
        settingsButton.setIcon(settingsIcon);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_item:
                startActivity(new Intent(getActivity(), PersonActivity.class));

                //((MainActivity)getActivity()).switchToMapFragment();
                System.out.println("search item clicked");
                break;

            case R.id.filter_item:
                //startActivity(new Intent(Database.this,SurveyActivity.class));
                System.out.println("filter item clicked");
                break;

            case R.id.settings_item:
                //startActivity(new Intent(Database.this,Stats.class));
                System.out.println("settings item clicked");
                break;
        }
        return true;
    }


    /** Called when the user clicks a marker. */
    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        System.out.println(marker.getTag() + " Marker Clicked");

        ModelData data = ModelData.getInstance( );
        String tag = marker.getTag().toString();
        Event event = data.getSpecificEvent(tag);
        mSendToPersonActivity = data.getSpecificPerson(event.getPersonID());

        if (mSendToPersonActivity.getGender().equals("m")) {
            Drawable personIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).colorRes(R.color.color_accent).sizeDp(20);
            mPersonIcon.setImageDrawable(personIcon);
        }
        else {
            Drawable personIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).colorRes(R.color.color_accent).sizeDp(20);
            mPersonIcon.setImageDrawable(personIcon);
        }

        mName.setText(mSendToPersonActivity.getFirstName() + " " + mSendToPersonActivity.getLastName());
        mEventDetails.setText(event.getEventType() + " " + event.getCity() + ", "
                + event.getCountry() + " (" + event.getYear() + ")");

        return false;
    }
}

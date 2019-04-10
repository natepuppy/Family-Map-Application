package com.example.familymapapplication;


import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;


public class MainActivity extends AppCompatActivity {
    FragmentManager fm = getSupportFragmentManager();
    LoginFragment loginFragment = new LoginFragment();
    MapFragment mapFragment = new MapFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm.beginTransaction().add(R.id.Fragment_Container, loginFragment).commit();

        Iconify.with(new FontAwesomeModule());
    }

    public void switchToMapFragment() {
        fm.beginTransaction().replace(R.id.Fragment_Container, mapFragment).commit();

        System.out.println("switchToMapFragment");
                // frag manager
    }
}

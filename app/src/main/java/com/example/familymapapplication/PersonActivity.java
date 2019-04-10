package com.example.familymapapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {
    private Person mPerson;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        mFirstName = findViewById(R.id.person_first_name);
        mLastName = findViewById(R.id.person_last_name);
        mGender = findViewById(R.id.person_gender);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            ModelData modelData = ModelData.getInstance( );
            mPerson = modelData.getSpecificPerson(value);
        }


        // set fields of the display
        mFirstName.setText(mPerson.getFirstName());
        mLastName.setText(mPerson.getLastName());
        if (mPerson.getGender().equalsIgnoreCase("m")) mGender.setText("Male");
        else mGender.setText("Female");

        ModelData data = ModelData.getInstance( );

        ArrayList<Person> immediateFamily =  data.getImmediateFamily(mPerson.getPersonID());
        ArrayList<Event> lifeEvents = data.getLifeEvents(mPerson.getPersonID());

        int i = 0;
        System.out.println(mPerson.getPersonID());

        for (Person person : immediateFamily) {
            if (i == 0) System.out.println(person.getFirstName() + " " + person.getLastName() + " Father");
            else if (i == 1) System.out.println(person.getFirstName() + " " + person.getLastName() + " Mother");
            else if (i == 2) System.out.println(person.getFirstName() + " " + person.getLastName() + " Spouse");
            else System.out.println(person.getFirstName() + " " + person.getLastName() + " Child");
            i++;
        }
        for (Event event : lifeEvents) {
            System.out.println(event.getEventType() + " " + event.getYear());
        }
    }
}

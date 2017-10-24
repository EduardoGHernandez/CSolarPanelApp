package com.android.csolarpanelapp.csolarpanelapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import static com.android.csolarpanelapp.csolarpanelapp.R.layout.activity_location_data;

public class LocationData extends AppCompatActivity {

    private TextView textViewshow;
    private Spinner city_spinner;
    private Spinner month_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_data);
        textViewshow = (TextView) findViewById(R.id.TextViewData);
        city_spinner = (Spinner) findViewById(R.id.SpinnerCity);
        month_spinner = (Spinner) findViewById(R.id.SpinnerMonth);

        ArrayAdapter<String> cities_array = new ArrayAdapter<String>(LocationData.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.cities));
        ArrayAdapter<String> months_array = new ArrayAdapter<String>(LocationData.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.month));

        cities_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        months_array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        city_spinner.setAdapter(cities_array);
        month_spinner.setAdapter(months_array);
    }

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference edo = ref.child("Guanajuato");

    public void Show_Data(View view)
    {


        DatabaseReference muni = edo.child(city_spinner.getSelectedItem().toString());
        DatabaseReference mes = muni.child(month_spinner.getSelectedItem().toString());

        DatabaseReference maat = mes.child("MAAT");
        DatabaseReference mari = mes.child("MARI");
        DatabaseReference minri = mes.child("MINRI");
        DatabaseReference maxri = mes.child("MAXRI");
        DatabaseReference madh = mes.child("MADH");

        textViewshow.setText("Monthly Average...\n\n");
        maat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double vmaat = dataSnapshot.getValue(Double.class);
                textViewshow.setText(textViewshow.getText() + "Air Temperature (°C):     " + vmaat.toString() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
        mari.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double vmari = dataSnapshot.getValue(Double.class);
                textViewshow.setText(textViewshow.getText() + "Radiation Incident (KWh/m2/day):     " + vmari.toString() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
        minri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double vminri = dataSnapshot.getValue(Double.class);
                textViewshow.setText(textViewshow.getText() + "Minimun Radiation (KWh/m2/day):   " + vminri.toString() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
        maxri.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double vmaxri = dataSnapshot.getValue(Double.class);
                textViewshow.setText(textViewshow.getText() + "Maximun Radiation (KWh/m2/day):   " + vmaxri.toString() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
        madh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double vmadh = dataSnapshot.getValue(Double.class);
                textViewshow.setText(textViewshow.getText() + "Daylight Hours:     " + vmadh.toString() + "\n");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to read value.", databaseError.toException());
            }
        });
    }
}
package com.example.biodump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recycle extends AppCompatActivity {
    TextView foodname,people,location;

    Spinner spinner;
    DatabaseReference dbref,psuedoref;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerData,listData;
    String state,phone;
    Integer flag=0;
    ListView listView;
    Button btncall;
    private static final int REQUEST_CALL_CODE =200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);

        foodname = findViewById(R.id.foodname6);
        people = findViewById(R.id.peep6);
        location = findViewById(R.id.loc6);


        dbref = FirebaseDatabase.getInstance().getReference("Food_ads");
       // psuedoref = dbref.child("Chittagong  City Corporation");
       // DatabaseReference altref = dbref.child(state);
        ValueEventListener event = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {


                    foodname.setText(ds.child("name").getValue(String.class));
                   // String p = "For "+ds.child("person").getValue(String.class)+" people";
                    int p = ((Long) ds.child("person").getValue()).intValue();
                    String p1 = String.valueOf(p);
                    people.setText("For " + p1 + " people");
                    location.setText(ds.child("location").getValue(String.class));




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbref.addListenerForSingleValueEvent(event);

        //initialize and assign variable for the bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.recycle);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.alternate:
                        startActivity(new Intent(getApplicationContext()
                                ,Alternate.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tree:
                        startActivity(new Intent(getApplicationContext()
                                ,tree.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,Homepg.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.recycle:
                        return true;
                }
                return false;
            }
        });
    }
}
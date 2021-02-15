package com.example.biodump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class tree extends AppCompatActivity {


    TextView objtv,cityname;
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
        setContentView(R.layout.activity_tree);
        spinner = findViewById(R.id.spinner);
        btncall = findViewById(R.id.callbtn);
        cityname = findViewById(R.id.corpname);
        //   listView =findViewById(R.id.listview);

        dbref = FirebaseDatabase.getInstance().getReference("City-Corporation");
        psuedoref = dbref.child("Chittagong  City Corporation");

        //initialize Spinner
        spinnerData = new ArrayList<>();
        adapter = new ArrayAdapter<String>(tree.this,R.layout.custom_spinner,spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerData.clear();
        retrievedata_spinner();
        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                state=  spinner.getSelectedItem().toString();
                DatabaseReference altref = dbref.child(state);
                ValueEventListener event = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds:dataSnapshot.getChildren())
                        {

                            phone = ds.child("phone").getValue(String.class);
                            cityname.setText(ds.child("name").getValue(String.class));

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                altref.addListenerForSingleValueEvent(event);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makephonecall();
            }
        });





        /*//Initialize Listview
        listData = new ArrayList<String>();

      //  listAdapter = new ArrayAdapter<String>(tree.this,R.layout.item,listData);
       final MyAdapter listAdapter = new MyAdapter(this,listData);
        listView.setAdapter(listAdapter);


        psuedoref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getKey();
                listData.add(value);
                listAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                listAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/




        //initialize and assign variable for the bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.tree);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.alternate:
                        startActivity(new Intent(getApplicationContext()
                                ,Alternate.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,Homepg.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.recycle:
                        startActivity(new Intent(getApplicationContext()
                                ,Recycle.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.tree:
                        return true;
                }
                return false;
            }
        });
    }

    public void makephonecall()
    {
        if(phone.length()>0)
        {
            if(ContextCompat.checkSelfPermission(tree.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(tree.this,
                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL_CODE);
            }
            else
            {
                String dial = "tel:"+phone;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
            }

        }
        else
        {

        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CALL_CODE)
        {
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                makephonecall();
            }
            else
            {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    public void retrievedata_spinner()
    {
        listener = dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot item:dataSnapshot.getChildren())
                {
                    spinnerData.add(item.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*class MyAdapter extends ArrayAdapter<String>{
        Context context;
        ArrayList<String> value;
        MyAdapter(Context c,ArrayList<String> v)
        {
            super(c,R.layout.item,R.id.corpname,v);
            this.context = c;
            this.value =v;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater =(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View item = layoutInflater.inflate(R.layout.item,parent,false);
            TextView citycorp = findViewById(R.id.corpname);
            citycorp.setText(value.get(position));


            return item;
        }
    }*/





}
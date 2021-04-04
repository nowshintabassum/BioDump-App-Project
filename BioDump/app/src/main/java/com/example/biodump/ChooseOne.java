package com.example.biodump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseOne extends AppCompatActivity {
    Button Citizen,City;
    Intent intent;
    String type;
    ConstraintLayout bgimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_one);

        intent = getIntent();
        type = intent.getStringExtra("Home").toString().trim();

        Citizen = (Button)findViewById(R.id.citizen);
        City = (Button)findViewById(R.id.city);


        Citizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals("Email")){
                    Intent citizenloginemail  = new Intent(ChooseOne.this,Citizenlogin.class);
                    startActivity(citizenloginemail);
                    finish();
                }
                if(type.equals("Phone")){
                    Intent citizenloginphone  = new Intent(ChooseOne.this,Citizenloginphone.class);
                    startActivity(citizenloginphone);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent citizenRegister  = new Intent(ChooseOne.this,CitizenRegistration.class);
                    startActivity(citizenRegister);
                }
            }
        });

        City.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(type.equals("Email")){
                    Intent Cityloginemail  = new Intent(ChooseOne.this,Homepg.class);
                    startActivity(Cityloginemail);
                    finish();
                }
                if(type.equals("Phone")){
                    Intent Cityloginphone  = new Intent(ChooseOne.this,CityLoginphone.class);
                    startActivity(Cityloginphone);
                    finish();
                }
                if(type.equals("SignUp")){
                    Intent CityRegister  = new Intent(ChooseOne.this,CityRegistration.class);
                    startActivity(CityRegister);
                }

            }
        });
    }
}
package com.example.biodump;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CityLogin extends AppCompatActivity {
    Button btnlogin;
    EditText emailcity,pwdcity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_login);
        btnlogin =(Button) findViewById(R.id.buttoncitylogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent citizenloginemail  = new Intent(CityLogin.this,Homepg.class);
                    startActivity(citizenloginemail);

            }
        });

    }
}
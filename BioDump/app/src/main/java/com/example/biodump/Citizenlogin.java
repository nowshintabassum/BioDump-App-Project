package com.example.biodump;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Citizenlogin extends AppCompatActivity {
    Button btnlogincitizen;
    EditText emailcitizen,pwdcitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizenlogin);
        btnlogincitizen =(Button) findViewById(R.id.citizenlogin);
        btnlogincitizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent citizenloginemail  = new Intent(Citizenlogin.this,Homepg.class);
                startActivity(citizenloginemail);

            }
        });
    }
}
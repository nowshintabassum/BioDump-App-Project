package com.example.biodump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;

public class CitizenRegistration extends AppCompatActivity {

    EditText mName,mEmail,mPass,cpass,mMobileno,mHouseno,mArea,pincode;
    Spinner Statespin,Cityspin;
    Button mSignup, mEmailbtn, mPhonebtn;
    CountryCodePicker Cpp;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String role="Citizen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_registration);
        mName = findViewById(R.id.Name);
        mEmail = findViewById(R.id.Email);
        mPass =findViewById(R.id.Pwd);
        mMobileno= findViewById(R.id.Mobileno);
        mHouseno=findViewById(R.id.houseNo);
        mArea=findViewById(R.id.Area);
        mSignup=findViewById(R.id.Signup);
        mEmailbtn=findViewById(R.id.emailbtn);
        mPhonebtn=findViewById(R.id.phonebtn);


        fAuth = FirebaseAuth.getInstance();
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),Homepg.class));
            finish();
        }

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= mEmail.getText().toString().trim();
                String password = mPass.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mPass.setError("Password is Required");
                    return;
                }
                if(password.length()<6){
                    mPass.setError("Password Must be >=6 Characters");
                    return;
                }
                // register

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(CitizenRegistration.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Homepg.class));

                        }
                        else {
                            Toast.makeText(CitizenRegistration.this,"Error Occurred"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });










    }
}
package com.example.biodump;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alternate extends AppCompatActivity {

    ImageView iv;
    private  static final int GALLERY_REQUEST_CODE =123;
    Button folder,camera,alt;
    TextView objtv,alt_text;
    Spinner spinner;
    DatabaseReference dbref,psuedoref;
    ValueEventListener listener;
    ArrayAdapter<String> adapter;
    ArrayList<String> spinnerData;
    String state;
    Integer flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate);

        iv = findViewById(R.id.object_pic);
        folder = findViewById(R.id.folder2);
        camera = findViewById(R.id.camera2);
        objtv = findViewById(R.id.objecttv);
        spinner = findViewById(R.id.spinner);
        alt = findViewById(R.id.alt_btn);
        alt_text = findViewById(R.id.alt_text);
           //intialize DB
        dbref = FirebaseDatabase.getInstance().getReference("Alternatives");
        psuedoref = FirebaseDatabase.getInstance().getReference("Pseudo");


        //initialize Spinner
        spinnerData = new ArrayList<>();
        adapter = new ArrayAdapter<String>(Alternate.this,R.layout.custom_spinner,spinnerData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerData.clear();
        retrievedata_spinner();
        adapter.notifyDataSetChanged();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                flag=0;
                objtv.setText("");
              state=  spinner.getSelectedItem().toString();
                DatabaseReference altref = dbref.child(state);
                ValueEventListener event = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String img_link = dataSnapshot.child("image").getValue(String.class);
                        Picasso.get().load(img_link).into(iv);

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


        //Request for camera permission
        if(ContextCompat.checkSelfPermission(Alternate.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Alternate.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Intent camintent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camintent,100);

            }
        });

        //Choose picture form gallery

        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "Pick an image"),GALLERY_REQUEST_CODE);

            }
        });
        //Alternative Button Clicked
        alt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  String state = spinner.getSelectedItem().toString();
               // String a = "Aerosol";

                if(flag==0) {

                    DatabaseReference altref = dbref.child(state);
                    ValueEventListener event = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String alt_name = dataSnapshot.child("Alternative").getValue(String.class);
                            objtv.setText(alt_name);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    altref.addListenerForSingleValueEvent(event);
                }
                else
                {
                    DatabaseReference altref = psuedoref.child(state);
                    ValueEventListener event = new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String alt_name = dataSnapshot.child("Alternative").getValue(String.class);
                            objtv.setText(alt_name);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    altref.addListenerForSingleValueEvent(event);

                }
            }
        });


        //initialize and assign variable for the bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.alternate);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.alternate:
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
                        startActivity(new Intent(getApplicationContext()
                                ,Recycle.class));
                        overridePendingTransition(0,0);
                }
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imagedata = data.getData();
            iv.setImageURI(imagedata);

            FirebaseVisionImage image;
            try {
                image = FirebaseVisionImage.fromFilePath(getApplicationContext(), imagedata);
                FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                      .getOnDeviceImageLabeler();

           //     Or, to set the minimum confidence required:
        // FirebaseVisionCloudImageLabelerOptions options =
        //        new FirebaseVisionCloudImageLabelerOptions.Builder()
          //           .setConfidenceThreshold(0.7f)
          //                       .build();
          //   FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
          //                   .getCloudImageLabeler();


                labeler.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                                // Task completed successfully
                                // ...
                                double con = 0.00;
                                objtv.setText("");


                                for (FirebaseVisionImageLabel label: labels) {
                                    String imgtext = label.getText();
                                    String entityId = label.getEntityId();
                                    double confidence = label.getConfidence();
                                    if(confidence>con)
                                    {

                                      //  objtv.setText(imgtext);
                                        con= confidence;
                                        state=imgtext;

                                    }

                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if(requestCode == 100)
        {
            Bitmap bitmap =(Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(bitmap);
            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
                    .getOnDeviceImageLabeler();

            //     Or, to set the minimum confidence required:
            // FirebaseVisionCloudImageLabelerOptions options =
            //        new FirebaseVisionCloudImageLabelerOptions.Builder()
            //           .setConfidenceThreshold(0.7f)
            //                       .build();
            //   FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance()
            //                   .getCloudImageLabeler();


            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            // ...
                            double con = 0.00;
                            objtv.setText("");


                            for (FirebaseVisionImageLabel label: labels) {
                                String imgtext = label.getText();
                                String entityId = label.getEntityId();
                                double confidence = label.getConfidence();

                                if(confidence>con)
                                {

                                  //  objtv.setText(imgtext);
                                    con= confidence;
                                    state=imgtext;

                                }


                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                        }
                    });
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




}
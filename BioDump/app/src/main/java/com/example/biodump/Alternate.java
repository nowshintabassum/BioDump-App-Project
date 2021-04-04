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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionCloudImageLabelerOptions;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;

import java.io.IOException;
import java.util.List;

public class Alternate extends AppCompatActivity {

    ImageView iv;
    private  static final int GALLERY_REQUEST_CODE =123;
    Button folder,camera;
    TextView objtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alternate);

        iv = findViewById(R.id.object_pic);
        folder = findViewById(R.id.folder2);
        camera = findViewById(R.id.camera2);
        objtv = findViewById(R.id.objecttv);

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
                Intent camintent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camintent,100);
            }
        });

        //Choose picture form gallery

        folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "Pick an image"),GALLERY_REQUEST_CODE);

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
                                    if(confidence>=con)
                                    {
                                        objtv.append(imgtext+" \n");
                                         con = confidence;
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
                                if(confidence>=con)
                                {
                                    objtv.append(imgtext+" \n");
                                    con = confidence;
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


}
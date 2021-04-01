package com.example.biodump;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addfood extends AppCompatActivity {
    private static int RESULT_LOAD_IMAGE = 1;
    EditText FoodLocation,FoodPerson,FoodName;
    Button Add;
    DatabaseReference reff;
    Food_ads food_ads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);

        FoodLocation = (EditText)findViewById(R.id.FoodLocation);
        FoodName = (EditText)findViewById(R.id.FoodName);
        FoodPerson = (EditText)findViewById(R.id.FoodPerson);
        Add = (Button)findViewById(R.id.FoodAddBtn);
        food_ads = new Food_ads();
        reff = FirebaseDatabase.getInstance().getReference().child("Food_ads");
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Person= Integer.parseInt(FoodPerson.getText().toString().trim());
                food_ads.setName(FoodName.getText().toString().trim());
                food_ads.setLocation(FoodLocation.getText().toString().trim());
                food_ads.setPerson(Person);

                reff.push().setValue(food_ads);
                Toast.makeText(addfood.this,"data inserted",Toast.LENGTH_LONG).show();
            }
        });



        Button buttonLoadImage = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.imgView);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}
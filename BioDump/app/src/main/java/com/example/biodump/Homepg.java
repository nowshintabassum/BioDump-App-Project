package com.example.biodump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Homepg extends AppCompatActivity {

    TextView pollution,pollutants,AQ,temperature,bigAQI,advice1,advice2,advice3,advice4;

    int pg;
    double Lattitude,Longitude;
    Button foodbtn;



    ProgressBar progress_bar;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepg);


        //INFO
        pollution = findViewById(R.id.pollution);
        bigAQI = findViewById(R.id.AQI_value);
        pollutants =findViewById(R.id.pollutants);
        AQ = findViewById(R.id.AQ);
        temperature = findViewById(R.id.temp);
        advice1 =findViewById(R.id.tv5);
        advice2 =findViewById(R.id.tv6);
        advice3 =findViewById(R.id.tv7);
        foodbtn = (Button)findViewById(R.id.FoodBtn);

        getWeather();
        //Progress Bar

        progress_bar=findViewById(R.id.progress_bar);



        //initialize and assign variable for the bottom nav
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

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
                    case R.id.recycle:
                        startActivity(new Intent(getApplicationContext()
                                ,Recycle.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                }
                return false;
            }
        });

    }
    public void  getWeather(){
        String url_weather ="http://api.openweathermap.org/data/2.5/weather?q=Dhaka&appid=c00853e1f967ca9bcc7e617eda334057";




        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url_weather, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                try {

                    JSONObject jsonObject1 = new JSONObject(response1);
                    JSONObject weatherobj1 = jsonObject1.getJSONObject("main");
                    int temp_find = weatherobj1.getInt("temp");
                    //     int temp_int = Integer.parseInt(country_find);
                    temp_find = temp_find-273;
                    String temp1 = temp_find + "°C";
                    temperature.setText(temp1);

                    JSONObject coordobj = jsonObject1.getJSONObject("coord");
                    Lattitude = coordobj.getDouble("lat");
                    Longitude = coordobj.getDouble("lon");
                } catch (JSONException e) {
                    e.printStackTrace();
                };

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Homepg.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue1 = Volley.newRequestQueue(Homepg.this);
        requestQueue1.add(stringRequest1);


        String url_pollution ="http://api.openweathermap.org/data/2.5/air_pollution?lat=23.7104&lon=90.4074&appid=c00853e1f967ca9bcc7e617eda334057";


        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url_pollution, new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {

                try {

                    JSONObject jsonObject2 = new JSONObject(response2);
                    JSONArray aqi_obj = jsonObject2.getJSONArray("list");
                    JSONObject aqi_obj1 = aqi_obj.getJSONObject(0);
                    JSONObject aqi_obj2 = aqi_obj1.getJSONObject("main");
                    String aqi_find = aqi_obj2.getString("aqi");

                    bigAQI.setText(aqi_find);
                    AQ.setText(aqi_find);
                    int aqi_int = Integer.parseInt(aqi_find);
                    if(aqi_int<=3)
                    {
                        pollution.setText("Low");
                        advice1.setText("Minimal Health Impact");
                        advice2.setText("May cause minor breathing discomfort to sensitive people");
                        advice3.setText("Enjoy usual Outdoor activities");

                        pg =25;
                    }
                    else if(aqi_int>=4 && aqi_int<7)
                    {
                        pollution.setText("Moderate");
                        advice1.setText("No impact on healthy people");
                        advice2.setText("Sensitive individuals will experience more serious conditions.");
                        advice3.setText("Consider reducing Outdoor activities");
                        pg=50;

                    }
                    else if(aqi_int>=7 && aqi_int<=10)
                    {
                        pollution.setText("High");
                        advice1.setText("May cause respiratory illness to  people on prolonged exposure");
                        advice2.setText(" People with lungs/heart diseases will be significantly affected");
                        advice3.setText("Reduce Outdoor activities");

                        pg=75;

                    }
                    else
                    {
                        pollution.setText("Very High");
                        advice1.setText("May cause respiratory impact even on healthy people");
                        advice2.setText("Serious health impacts on people with lung/heart disease");
                        advice3.setText("Avoid going Outdoor");

                        pg=100;

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Homepg.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue2 = Volley.newRequestQueue(Homepg.this);
        requestQueue2.add(stringRequest2);
        final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(i<=pg)
                {
                    progress_bar.setProgress(i);
                    i++;
                    handler.postDelayed(this,30);
                }
                else{
                    handler.removeCallbacks(this);
                }
            }
        },30);
        foodbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent food  = new Intent(Homepg.this,Food.class);
                    startActivity(food);
                    finish();

            }
        });
    }

}



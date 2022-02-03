package com.example.forecastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ImageView btnSerch;
    TextView tvtemp,tvcity,tvmist,tvtodayweather;
    ImageView iconweather,IVback;
    EditText etcityname;
    ImageView btnshare,btnsee;

    RecyclerView weatherrv;
    myAdaptar myadaptar;



    String share_cityname;
    double share_temp;
    String share_mist;
    String share_time;


    ArrayList<WeatherModel> weatherModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();
        btnSerch = findViewById(R.id.btnSearch);
        tvtemp = findViewById(R.id.idtvTemperature);
        tvcity = findViewById(R.id.tvCityName);
        etcityname = findViewById(R.id.etCityName);
        iconweather = findViewById(R.id.iconWeather);
        IVback = findViewById(R.id.idIVBack);
        tvmist= findViewById(R.id.idtvmist);
        btnshare = findViewById(R.id.btnshare);
        btnsee = findViewById(R.id.btnsee);
       tvtodayweather = findViewById(R.id.idtvtodayweather);



        weatherModelArrayList = new ArrayList<>();
        weatherrv = findViewById(R.id.idweatherRv);
        weatherrv.hasFixedSize();

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
//        weatherrv.setLayoutManager(gridLayoutManager);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        weatherrv.setLayoutManager(linearLayoutManager);






        btnSerch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = etcityname.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please Enter City Name",Toast.LENGTH_SHORT).show();
                }else{
                    loadweatherinfo(city);


//

                }
            }
        });

//
    }

    private void loadweatherinfo(String city) {

        Ion.with(MainActivity.this)
                .load("http://api.weatherapi.com/v1/forecast.json?key=0e4f35afd7bc4704bb3171408220202&q=" + city+"&days=1&aqi=no&alerts=no")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // do stuff with the result or error
                       // Log.d("result",result.toString());

                        if(e !=null){
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                        }else{
                            JsonObject current = result.get("current").getAsJsonObject();
                            double temperature = current.get("temp_c").getAsDouble();
                            tvtemp.setText(temperature+"°C");


                            share_temp = temperature;


                            int is_day = current.get("is_day").getAsInt();
                            if(is_day==0){
                                IVback.setImageResource(R.drawable.night);
                            }else{
                                IVback.setImageResource(R.drawable.real_day);
                            }

                            JsonObject location = result.get("location").getAsJsonObject();
                            String name = location.get("name").getAsString();
                            String country = location.get("country").getAsString();
                            tvcity.setText(name+", "+ country);


                            share_cityname =name+","+ country;

                            JsonObject condition = current.get( "condition").getAsJsonObject();

                            String mist_condition = condition.get("text").getAsString();
                            tvmist.setText(mist_condition);
                            share_mist = mist_condition;

                            JsonObject localaction = result.get("location").getAsJsonObject();
                            String local_time = location.get("localtime").getAsString();
                            share_time = local_time;


                            String iconurl = condition.get("icon").getAsString();
                            Glide.with(MainActivity.this).load("https:"+iconurl).into(iconweather);



                            JsonObject forcast_obj = result.get("forecast").getAsJsonObject();
                            JsonArray forcast_day = forcast_obj.get("forecastday").getAsJsonArray();
                            JsonObject forcast0 = forcast_day.get(0).getAsJsonObject();

                            JsonArray hour_array = forcast0.get( "hour").getAsJsonArray();

                            for (int i=0;i<hour_array.size();i++){
                                JsonObject hour_obj = hour_array.get(i).getAsJsonObject();
                                double windspeed = hour_obj.get("wind_kph").getAsDouble();

                                JsonObject condition_obj = hour_obj.get("condition").getAsJsonObject();
                                String icon_url = condition_obj.get("icon").getAsString();

                                String time = hour_obj.get("time").getAsString();
                                double temp = hour_obj.get("temp_c").getAsDouble();

                                weatherModelArrayList.add(new WeatherModel(time,windspeed,icon_url,temp));


                            }

                            tvtodayweather.setVisibility(View.VISIBLE);

                            myadaptar = new myAdaptar(MainActivity.this,weatherModelArrayList);
                            weatherrv.setAdapter(myadaptar);


                            btnshare.setVisibility(View.VISIBLE);
                            btnshare.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {


                                    //TODO  intent for send onlY  to mail

//                                    Intent intent = new Intent();
//                                    intent.setAction(Intent.ACTION_SENDTO);
//                                    intent.setData(Uri.parse("mailto:"));
                                  //  intent.setPackage("com.whatsapp");
//                                    intent.putExtra(Intent.EXTRA_SUBJECT,"Current Weather Forecast At : " + share_cityname);
//                                    intent.putExtra(Intent.EXTRA_TEXT," Current  Weather Temperature  of  "+ share_cityname+ "at" +share_time+" is " +share_temp +" and is about to  " +share_mist);
//                                    startActivity(intent);

//


                                    //TODO intent  SEND ONLY FOR WHATSAPP

                                    Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                                    sendIntent.setType("text/plain");
                                    startActivity(sendIntent);
                                  // sendIntent.setPackage("com.whatsapp");

                                    sendIntent.putExtra(Intent.EXTRA_SUBJECT,"Current Weather Forecast At : " + share_cityname);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT," Current  Weather Temperature  of  "+ share_cityname+ " at " +share_time+" is " +share_temp +" and is about to  " +share_mist);
                                    startActivity(Intent.createChooser(sendIntent, ""));



                                }
                            });


                            btnsee.setVisibility(View.VISIBLE);
                            btnsee.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    double latitude = location.get("lat").getAsDouble();
                                    double longitude = location.get("lon").getAsDouble();


                                  String url = "https://www.google.com/maps/search/?api=1&query="+latitude+","+longitude ;


                                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                                    CustomTabsIntent customTabsIntent = builder.build();
                                    customTabsIntent.launchUrl(MainActivity.this, Uri.parse(url));
                                }
                            });


                        }
                    }
                });
    }
}
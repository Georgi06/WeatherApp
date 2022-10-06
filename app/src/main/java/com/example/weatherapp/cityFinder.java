package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;



public class  cityFinder extends AppCompatActivity {

    final String APP_ID = "dab3af44de7d24ae7ff86549334e45bd";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";


    String Location_Provider = LocationManager.GPS_PROVIDER;

    TextView cityName, weatherCondition, temp;
    ImageView weatherIcon;
    EditText newCity;
    Button btnSearch;


    LocationManager mLocationManager;
    LocationListener mLocationListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        //EditText text = findViewById(R.id.searchCityET);
       // String newCity = text.getText().toString();

        newCity = findViewById(R.id.searchCityET);
        weatherCondition = findViewById(R.id.weatherConditionTV);
        temp = findViewById(R.id.temperatureTV);
        weatherIcon = findViewById(R.id.weatherIconIV);
        cityName = findViewById(R.id.cityNameTV);

        newCity.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String nCity = newCity.getText().toString();
                if (nCity.isEmpty()){
                    Toast.makeText(cityFinder.this, "Please enter city", Toast.LENGTH_SHORT).show();
                }else{
                    getWeatherForNewCity(nCity);
                }
            }
        });

    }

    float x1, y1, x2, y2;
    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    Intent i = new Intent(cityFinder.this, MainActivity.class);
                    startActivity(i);
                    break;
                }
        }
        return false;
    }



    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent=getIntent();
        String city= mIntent.getStringExtra("City");
        if(city!=null)
        {
            getWeatherForNewCity(city);
        }
        else
        {
            Toast.makeText(this, "No such city", Toast.LENGTH_SHORT).show();
        }


    }


    private void getWeatherForNewCity(String newCity)
    {
        RequestParams params=new RequestParams();
        params.put("q",newCity);
        params.put("appid",APP_ID);
        letsdoSomeNetworking(params);

    }


    private  void letsdoSomeNetworking(RequestParams params)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                Toast.makeText(cityFinder.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                weatherData weatherD=weatherData.fromJson(response);
                updateUI(weatherD);


                // super.onSuccess(statusCode, headers, response);
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            }
        });



    }

    private  void updateUI(weatherData weather){


        temp.setText(weather.getApiTemp());
        cityName.setText(weather.getApiCity());
        weatherCondition.setText(weather.getApiWeatherType());
        int resourceID=getResources().getIdentifier(weather.getApiIcon(),"drawable",getPackageName());
        weatherIcon.setImageResource(resourceID);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mLocationManager!=null)
        {
            mLocationManager.removeUpdates(mLocationListner);
        }
    }
}
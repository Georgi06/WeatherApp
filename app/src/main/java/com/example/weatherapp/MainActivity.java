package com.example.weatherapp;


import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.core.app.ActivityCompat;

    import android.Manifest;
    import android.content.Context;
    import android.content.Intent;
    import android.content.pm.PackageManager;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
    import android.widget.Toast;

    import com.loopj.android.http.AsyncHttpClient;
    import com.loopj.android.http.JsonHttpResponseHandler;
    import com.loopj.android.http.RequestParams;

    import org.json.JSONObject;

    import cz.msebera.android.httpclient.Header;

    public class MainActivity extends AppCompatActivity {

        //primer https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}

        //final String API = "4c3bc7d93e7e95b8d4f07bef55ad5040";
        // final String weatherLink = "https://api.openweathermap.org/data/2.5/weather";

        final String APP_ID = "dab3af44de7d24ae7ff86549334e45bd";
        final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

        final long MIN_TIME = 5000;
        final float MIN_DISTANCE = 1000;
        final int REQUEST_CODE = 101;

        String Location_Provider = LocationManager.GPS_PROVIDER;

        TextView cityName, weatherCondition, temp;
        ImageView weatherIcon;

        LocationManager mLocationManager;
        LocationListener mLocationListner;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            weatherCondition = findViewById(R.id.weatherConditionTV);
            temp = findViewById(R.id.temperatureTV);
            weatherIcon = findViewById(R.id.weatherIconIV);
            cityName = findViewById(R.id.cityNameTV);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS},REQUEST_CODE);
                return;
            }


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
                    if (x1 > x2) {
                        Intent i = new Intent(MainActivity.this, cityFinder.class);
                        startActivity(i);
                        break;
                    }if(x1 < x2){
                    Intent i = new Intent(MainActivity.this, citySave.class);
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
                getWeatherForCurrentLocation();
            }

        }


        private void getWeatherForNewCity(String city)
        {
            RequestParams params=new RequestParams();
            params.put("q",city);
            params.put("appid",APP_ID);
            letsdoSomeNetworking(params);

        }




        private void getWeatherForCurrentLocation() {

            mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mLocationListner = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    String Latitude = String.valueOf(location.getLatitude());
                    String Longitude = String.valueOf(location.getLongitude());

                    RequestParams params =new RequestParams();
                    params.put("lat" ,Latitude);
                    params.put("lon",Longitude);
                    params.put("appid",APP_ID);
                    letsdoSomeNetworking(params);
                    
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };


            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
                return;
            }

            mLocationManager.requestLocationUpdates(Location_Provider, MIN_TIME, MIN_DISTANCE, mLocationListner);

        }


        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);


            if(requestCode==REQUEST_CODE)
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    getWeatherForCurrentLocation();
                    Toast.makeText(MainActivity.this,"Location get Successful",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }


        }



        private  void letsdoSomeNetworking(RequestParams params)
        {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(WEATHER_URL,params,new JsonHttpResponseHandler()
            {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    Toast.makeText(MainActivity.this,"Data Get Success",Toast.LENGTH_SHORT).show();

                    weatherData weatherD=weatherData.fromJson(response);
                    assert weatherD != null;
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
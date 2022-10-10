package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class citySave extends AppCompatActivity implements selectListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<citySaveModel> listItems;
    EditText saveCity;
    TextView savedCity;
    Button btnAdd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_save);

        recyclerView = findViewById(R.id.recyclerView);
        saveCity = findViewById(R.id.newCityET);
        savedCity = findViewById(R.id.savedCityTV);
        btnAdd = findViewById(R.id.btnAdd);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

       // listItems.add(new citySaveModel("Varna", "C"));
        //initData();

        adapter = new citySaveAdapter(listItems, this,this);

        recyclerView.setAdapter(adapter);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("myChannel","MyNotify", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        btnAdd.setOnClickListener(v -> {
            listItems.add(new citySaveModel(saveCity.getText().toString()));
            adapter.notifyItemInserted(listItems.size()-1);



            NotificationCompat.Builder  builder = new NotificationCompat.Builder(citySave.this,"Demo Notification");
            builder.setContentTitle("Item added");
            builder.setContentText("Demo");
            builder.setSmallIcon(R.drawable.sunny);
            builder.setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(citySave.this);
            managerCompat.notify(1,builder.build());
        });


//        findViewById(R.id.btnAdd).setOnClickListener(view ->{
//
//            listItems.add(new citySaveModel(saveCity.getText().toString()));
//            adapter.notifyItemInserted(listItems.size()-1);
//
//            NotificationCompat.Builder = builder = new NotificationCompat.Builder(MainActivity.this,"Demo Notification");


    }




    //    private void initData() {
//        listItems = new ArrayList<>();
//
//        listItems.add(new citySaveModel("V", "C"));
//        listItems.add(new citySaveModel("Varna", "C"));
//    }


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
                    Intent i = new Intent(citySave.this, MainActivity.class);
                    startActivity(i);
                    break;
                }
        }
        return false;
    }


    @Override
    public void onItemClick(int position) {
        String newCity= saveCity.getText().toString();
        Intent intent=new Intent(citySave.this,MainActivity.class);
        intent.putExtra("City",newCity);
        startActivity(intent);
    }

//    @Override
//    public void onBtnClick(int position) {
//
//    }
}
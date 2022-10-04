package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class citySave extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<citySaveModel> listItems;
    EditText saveCity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_save);
        recyclerView = findViewById(R.id.recyclerView);
        saveCity = findViewById(R.id.newCityET);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

       // listItems.add(new citySaveModel("Varna", "C"));
        //initData();

        adapter = new citySaveAdapter(listItems, this);

        recyclerView.setAdapter(adapter);

        findViewById(R.id.btnAdd).setOnClickListener(view ->{

            listItems.add(new citySaveModel(saveCity.getText().toString(),"C"));
            adapter.notifyItemInserted(listItems.size()-1);
        });

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



}
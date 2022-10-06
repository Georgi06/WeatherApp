package com.example.weatherapp;

public class citySaveModel {
    private String savedCityTV;

    citySaveModel(String savedCityTV){
        this.savedCityTV = savedCityTV;
    }

    public static void remove(int position) {
    }

    public String getSavedCityTV() {
        return savedCityTV;
    }


}

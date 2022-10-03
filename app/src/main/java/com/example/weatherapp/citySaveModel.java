package com.example.weatherapp;

public class citySaveModel {
    private String savedCityTV;
    private String degreesTV;

    citySaveModel(String savedCityTV,String degreesTV){
        this.savedCityTV = savedCityTV;
        this.degreesTV = degreesTV;
    }

    public String getSavedCityTV() {
        return savedCityTV;
    }

    public String getDegreesTV() {
        return degreesTV;
    }
}

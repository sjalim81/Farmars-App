package com.example.farmersapp.util;

import android.app.Application;

import com.google.firebase.firestore.GeoPoint;

public class CurrentUserApi extends Application {

    private String name;
    private String phoneNumber;
    private String userId;
    private String address_of_instrument;
    private GeoPoint geoPoint_of_instrument;
    private static  CurrentUserApi instance;

    public static CurrentUserApi getInstance() {
        if(instance == null)
            instance = new CurrentUserApi();
        return instance;
    }

    public CurrentUserApi(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress_of_instrument() {
        return address_of_instrument;
    }

    public void setAddress_of_instrument(String address_of_instrument) {
        this.address_of_instrument = address_of_instrument;
    }

    public GeoPoint getGeoPoint_of_instrument() {
        return geoPoint_of_instrument;
    }

    public void setGeoPoint_of_instrument(GeoPoint geoPoint_of_instrument) {
        this.geoPoint_of_instrument = geoPoint_of_instrument;
    }
}

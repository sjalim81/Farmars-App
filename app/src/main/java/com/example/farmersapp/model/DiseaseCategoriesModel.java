package com.example.farmersapp.model;

import java.util.List;

public class DiseaseCategoriesModel {


    public List<String> charaRoponDhap;
    public  List<String> folonDhap;
    public  List<String> fosolKatarDhap;
    public  List<String> pushpoDhap;
    public  List<String> udhvidhBordhonDhap;

    public DiseaseCategoriesModel() {
    }

    public DiseaseCategoriesModel(List<String> charaRoponDhap, List<String> folonDhap, List<String> fosolKatarDhap, List<String> pushpoDhap, List<String> udhvidhBordhonDhap) {
        this.charaRoponDhap = charaRoponDhap;
        this.folonDhap = folonDhap;
        this.fosolKatarDhap = fosolKatarDhap;
        this.pushpoDhap = pushpoDhap;
        this.udhvidhBordhonDhap = udhvidhBordhonDhap;
    }

    public List<String> getCharaRoponDhap() {
        return charaRoponDhap;
    }

    public List<String> getFolonDhap() {
        return folonDhap;
    }

    public List<String> getFosolKatarDhap() {
        return fosolKatarDhap;
    }

    public List<String> getPushpoDhap() {
        return pushpoDhap;
    }

    public List<String> getUdhvidhBordhonDhap() {
        return udhvidhBordhonDhap;
    }
}

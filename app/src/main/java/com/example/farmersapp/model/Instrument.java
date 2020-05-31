package com.example.farmersapp.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class Instrument {
   private String instrumentId, instrumentType, instrumentPrice, instrumentImageUrl
           , instrumentAddress;
   private Timestamp instrumentTimeAdded;
   private GeoPoint instrumentGeoPoint;
   private String ownerId, ownerName, ownerPhone;

    public Instrument() {
    }

    public Instrument(String instrumentId,String instrumentType, String instrumentPrice, String instrumentImageUrl, String instrumentAddress,
                      Timestamp instrumentTimeAdded, GeoPoint instrumentGeoPoint, String ownerId, String ownerName, String ownerPhone) {
        this.instrumentId = instrumentId;
        this.instrumentType = instrumentType;
        this.instrumentPrice = instrumentPrice;
        this.instrumentImageUrl = instrumentImageUrl;
        this.instrumentAddress = instrumentAddress;
        this.instrumentTimeAdded = instrumentTimeAdded;
        this.instrumentGeoPoint = instrumentGeoPoint;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getInstrumentPrice() {
        return instrumentPrice;
    }

    public void setInstrumentPrice(String instrumentPrice) {
        this.instrumentPrice = instrumentPrice;
    }

    public String getInstrumentImageUrl() {
        return instrumentImageUrl;
    }

    public void setInstrumentImageUrl(String instrumentImageUrl) {
        this.instrumentImageUrl = instrumentImageUrl;
    }

    public String getInstrumentAddress() {
        return instrumentAddress;
    }

    public void setInstrumentAddress(String instrumentAddress) {
        this.instrumentAddress = instrumentAddress;
    }

    public Timestamp getInstrumentTimeAdded() {
        return instrumentTimeAdded;
    }

    public void setInstrumentTimeAdded(Timestamp instrumentTimeAdded) {
        this.instrumentTimeAdded = instrumentTimeAdded;
    }

    public GeoPoint getInstrumentGeoPoint() {
        return instrumentGeoPoint;
    }

    public void setInstrumentGeoPoint(GeoPoint instrumentGeoPoint) {
        this.instrumentGeoPoint = instrumentGeoPoint;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}

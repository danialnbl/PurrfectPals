package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

public class PetSchedule {

    private String Key,Title,PetName,EvenDesc,EventLocation, Date,UserId;

    public PetSchedule(String key, String title, String petName, String evenDesc, String eventLocation, String date, String userId) {
        Key = key;
        Title = title;
        PetName = petName;
        EvenDesc = evenDesc;
        EventLocation = eventLocation;
        Date = date;
        UserId = userId;
    }

    public PetSchedule(String title, String petName, String evenDesc, String eventLocation, String date, String userId) {
        Title = title;
        PetName = petName;
        EvenDesc = evenDesc;
        EventLocation = eventLocation;
        Date = date;
        UserId = userId;
    }

    public PetSchedule() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetName(String petName) {
        PetName = petName;
    }

    public String getEvenDesc() {
        return EvenDesc;
    }

    public void setEvenDesc(String evenDesc) {
        EvenDesc = evenDesc;
    }

    public String getEventLocation() {
        return EventLocation;
    }

    public void setEventLocation(String eventLocation) {
        EventLocation = eventLocation;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }
}

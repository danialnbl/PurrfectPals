package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

public class VaccinationSchedule {
    private String Key, Title, PetName, Date, UserId;

    public VaccinationSchedule(){}
    public VaccinationSchedule(String key, String title, String petName, String date, String userId) {
        Key = key;
        Title = title;
        PetName = petName;
        Date = date;
        UserId = userId;
    }

    public VaccinationSchedule(String title, String petName, String date, String userId) {
        Title = title;
        PetName = petName;
        Date = date;
        UserId = userId;
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

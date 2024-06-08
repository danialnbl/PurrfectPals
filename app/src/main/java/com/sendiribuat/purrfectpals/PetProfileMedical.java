package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

public class PetProfileMedical {

    private String Illness, Date, UserId, Key, PetId;

    public PetProfileMedical() {

    }
    public PetProfileMedical(String illness, String date, String userId) {
        Illness = illness;
        Date = date;
        UserId = userId;
    }

    public String getIllness() {
        return Illness;
    }
    public void setIllness(String illness) {
        Illness = illness;
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

    public String getPetId() {
        return PetId;
    }

    public void setPetId(String petId) {
        PetId = petId;
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

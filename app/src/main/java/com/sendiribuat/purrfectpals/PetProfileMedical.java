package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class PetProfileMedical implements Serializable {

    private String Illness, Date, Key, PetId;

    public PetProfileMedical() {

    }
    public PetProfileMedical(String illness, String date) {
        Illness = illness;
        Date = date;
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

    @Exclude
    public String getKey() {
        return Key;
    }

    @Exclude
    public void setKey(String key) {
        Key = key;
    }
}

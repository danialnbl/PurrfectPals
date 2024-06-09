package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class PetProfileMedication implements Serializable {

    private String Medication, Dosage, UserId, Key, PetId;

    public PetProfileMedication() {

    }

    public PetProfileMedication(String medication, String dosage) {
        Medication = medication;
        Dosage = dosage;
    }

    public String getMedication() {
        return Medication;
    }

    public void setMedication(String medication) {
        Medication = medication;
    }


    public String getDosage() {
        return Dosage;
    }

    public void setDosage(String dosage) {
        Dosage = dosage;
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

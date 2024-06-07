package com.sendiribuat.purrfectpals;

public class PetProfileMedication {

    private String Medication, Dosage, UserId;

    public PetProfileMedication(String medication, String dosage, String userId) {
        Medication = medication;
        Dosage = dosage;
        UserId = userId;
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


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

}

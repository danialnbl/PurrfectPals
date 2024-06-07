package com.sendiribuat.purrfectpals;

public class PetProfileMedical {

    private String Illness, Date, UserId;

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
}

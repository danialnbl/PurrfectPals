package com.sendiribuat.purrfectpals;

public class VaccinationSchedule {
    private String Title, PetName, Date, UserId;

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
}

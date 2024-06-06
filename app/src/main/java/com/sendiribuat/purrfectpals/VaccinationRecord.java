package com.sendiribuat.purrfectpals;

public class VaccinationRecord {
    private String Title,PetName,Location,Date, UserId;

    public VaccinationRecord(String title, String petName, String location, String date, String userId) {
        Title = title;
        PetName = petName;
        Location = location;
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

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
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

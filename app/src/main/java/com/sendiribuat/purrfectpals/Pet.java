package com.sendiribuat.purrfectpals;

import java.util.List;

public class Pet {

    private String PetName, PetAnimalType, PetBreed, PetGender, PetColor, OwnerName, OwnerNum, OwnerEmail, UserId;
    private List<PetProfileMedical> medicalHistory;
    private List<PetProfileMedication> medications;

    private int PetAge;

    public Pet() {

    }

    public Pet(String petName, String petAnimalType, String petBreed, String petGender, String petColor, String ownerName, String ownerNum, String ownerEmail, String userId, int petAge) {
        PetName = petName;
        PetAnimalType = petAnimalType;
        PetBreed = petBreed;
        PetGender = petGender;
        PetColor = petColor;
        UserId = userId;
        PetAge = petAge;
    }

    public String getPetName() {
        return PetName;
    }

    public void setPetName(String petName) {
        PetName = petName;
    }

    public String getPetAnimalType() {
        return PetAnimalType;
    }

    public void setPetAnimalType(String petAnimalType) {
        PetAnimalType = petAnimalType;
    }



    public String getPetBreed() {
        return PetBreed;
    }
    public void setPetBreed(String petBreed) {
        PetBreed = petBreed;
    }


    public String getPetGender() {
        return PetGender;
    }
    public void setPetGender(String petGender) {
        PetGender = petGender;
    }

    public String getPetColor() { return PetColor; }
    public void setPetColor(String petColor) {
        PetBreed = petColor;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getPetAge() {
        return PetAge;
    }

    public void setPetAge(int petAge) {
        PetAge = petAge;
    }


    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }


    public String getOwnerNum() {
        return OwnerNum;
    }

    public void setOwnerNum(String ownerNum) {
        OwnerNum = ownerNum;
    }

    public String getOwnerEmail() {
        return OwnerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        OwnerEmail = ownerEmail;
    }


    public List<PetProfileMedical> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<PetProfileMedical> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public List<PetProfileMedication> getMedications() {
        return medications;
    }

    public void setMedications(List<PetProfileMedication> medications) {
        this.medications = medications;
    }
}

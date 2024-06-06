package com.sendiribuat.purrfectpals;

public class Pet {
    private String PetName, PetAnimalType, UserId;
    private int PetAge;

    public Pet(String petName, String petAnimalType, String userId, int petAge) {
        PetName = petName;
        PetAnimalType = petAnimalType;
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
}

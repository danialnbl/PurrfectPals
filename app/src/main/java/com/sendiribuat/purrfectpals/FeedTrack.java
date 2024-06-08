package com.sendiribuat.purrfectpals;

import com.google.firebase.database.Exclude;

public class FeedTrack {
    private String feedName, feedPet, foodDesc, feedTime, feedDate, userId, key;

    public FeedTrack(String feedName, String feedPet, String foodDesc, String feedTime, String feedDate, String userId, String key) {
        this.feedName = feedName;
        this.feedPet = feedPet;
        this.foodDesc = foodDesc;
        this.feedTime = feedTime;
        this.feedDate = feedDate;
        this.userId = userId;
        this.key = key;
    }

    public FeedTrack(String feedName, String feedPet, String foodDesc, String feedTime, String feedDate, String userId) {
        this.feedName = feedName;
        this.feedPet = feedPet;
        this.foodDesc = foodDesc;
        this.feedTime = feedTime;
        this.feedDate = feedDate;
        this.userId = userId;
    }

    public FeedTrack() {
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public String getFeedPet() {
        return feedPet;
    }

    public void setFeedPet(String feedPet) {
        this.feedPet = feedPet;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
    }

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }

    public String getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(String feedDate) {
        this.feedDate = feedDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }
}

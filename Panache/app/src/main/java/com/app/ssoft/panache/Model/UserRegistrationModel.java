package com.app.ssoft.panache.Model;

/**
 * Created by shekharshrivastava on 06/04/18.
 */

public class UserRegistrationModel {
    public static String registration = "user_registration";
    public String userId;
    public String userName;
    public String emailId;
    public String mobNumber;
    public String gender;
    public String imei;
    public String deviceModel;


    public UserRegistrationModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobNumber() {
        return mobNumber;
    }

    public void setMobNumber(String mobNumber) {
        this.mobNumber = mobNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserRegistrationModel(String userId, String userName, String emailId, String mobNumber, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.emailId = emailId;
        this.mobNumber = mobNumber;
        this.gender = gender;
    }
}

package com.example.learnprogramming.model;

public class UserProfile {

    private String name;
    private String email;
    private Long mobileNumber;
    private  String university;

    public UserProfile() {
    }

    public UserProfile(String name, String email, Long mobileNumber, String university) {
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.university = university;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}

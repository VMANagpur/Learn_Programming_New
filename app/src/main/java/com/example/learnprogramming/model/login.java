package com.example.learnprogramming.model;

public class login {

    private String email;
    private String password;

    private User user;

    public login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public login(String email, String password, User user) {
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

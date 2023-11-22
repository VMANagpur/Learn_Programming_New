package com.example.learnprogramming.retrofit;

import android.util.Log;

import com.example.learnprogramming.model.User;
import com.example.learnprogramming.model.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @POST("/registration")
    Call<String> getRegister(@Body User user);
}

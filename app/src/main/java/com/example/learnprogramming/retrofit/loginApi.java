package com.example.learnprogramming.retrofit;

import com.example.learnprogramming.model.LoginResponse;
import com.example.learnprogramming.model.User;
import com.example.learnprogramming.model.login;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface loginApi {

    @POST("/login")
    Call<LoginResponse> getLogin(@Body login login);
}

package com.example.learnprogramming.retrofit;

import com.example.learnprogramming.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface PersonalDetailsAPI {

//    @GET("/profile")
//    Call<User>getProfile(@Body User user);
@PUT("/update_personal_details")
Call<User> updateProfile(@Body User user);

//    @PUT("/profile")
//    Call<User>putProfile(@Body User user);


}

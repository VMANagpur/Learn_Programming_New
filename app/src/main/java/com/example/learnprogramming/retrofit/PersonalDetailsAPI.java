package com.example.learnprogramming.retrofit;

import com.example.learnprogramming.model.User;
import com.example.learnprogramming.model.UserProfile;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PersonalDetailsAPI {

    @PUT("/profile/{userId}")
    Call<UserProfile> updateProfile(@Path("userId") Long userId ,@Body UserProfile userProfile);
}

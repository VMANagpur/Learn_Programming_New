package com.example.learnprogramming.retrofit;

import com.example.learnprogramming.model.UserPrivacy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PrivacyDetailApi {

    @PUT("/privacy/{userId}")
    Call<UserPrivacy> updatePrivacy(@Path("userId") Long userId , @Body UserPrivacy userPrivacy);
}

package com.example.learnprogramming.retrofit

import com.example.learnprogramming.model.MockTestItem
import com.example.learnprogramming.model.MockTestItemItem
import retrofit2.Call
import retrofit2.http.GET

interface MockTestApi {

    @GET("/paper-sets")
    fun getMockTestItem():Call<List<MockTestItemItem>>

}
package com.example.learnprogramming.retrofit

import com.example.learnprogramming.model.MockTestItemItem
import com.example.learnprogramming.model.TestDetailsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MockTestApi {

    @GET("/paper-sets")
    fun getMockTestItem():Call<List<MockTestItemItem>>

    @GET("/{mockTestId}")
    fun getPaperSetDetails(@Path("mockTestId") mockTestId:Long):Call<TestDetailsResponse>

    @GET("/user/canAttempt")
    fun userEligibleForDayTest(@Query("userId")userId:Long,@Query("paperId")paperId:Long):Call<String>

}
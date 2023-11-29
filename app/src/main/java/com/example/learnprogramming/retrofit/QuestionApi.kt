package com.example.learnprogramming.retrofit
import com.example.learnprogramming.model.NoOfQuestionItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuestionApi {

    @GET("/exam/{mocktestquestion}")
    fun getAllMockTestQuestion(@Path("mocktestquestion") mockTestId:Long):Call<List<NoOfQuestionItem>>

}
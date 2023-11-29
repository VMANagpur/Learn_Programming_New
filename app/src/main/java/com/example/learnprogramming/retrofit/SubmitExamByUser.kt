package com.example.learnprogramming.retrofit

import com.example.learnprogramming.model.UserSubmittedExam
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SubmitExamByUser  {

    @POST("/user/submitUserAnswer")
    fun submitExam(@Body submitTest:UserSubmittedExam):Call<String>
}
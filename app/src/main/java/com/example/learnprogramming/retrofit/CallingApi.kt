package com.example.learnprogramming.retrofit

object CallingApi {

    val retrofitService = RetrofitService()

    val MockTestApi = retrofitService.retrofit.create(MockTestApi::class.java)

}
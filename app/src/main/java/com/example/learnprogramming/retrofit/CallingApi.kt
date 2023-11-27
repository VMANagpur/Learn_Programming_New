package com.example.learnprogramming.retrofit

object CallingApi {

    val retrofitService = RetrofitService()

    val MockTestApi = retrofitService.retrofit.create(MockTestApi::class.java)
    val userUpdate = retrofitService.retrofit.create(PersonalDetailsAPI::class.java)
    val userPrivacyUpdate = retrofitService.retrofit.create(PrivacyDetailApi::class.java)
}
package com.example.learnprogramming.retrofit

import retrofit2.create

object CallingApi {

    val retrofitService = RetrofitService()

    val MockTestApi = retrofitService.retrofit.create(MockTestApi::class.java)

    val QuestionApi = retrofitService.retrofit.create(QuestionApi::class.java)

    val userUpdate = retrofitService.retrofit.create(PersonalDetailsAPI::class.java)

    val userPrivacyUpdate = retrofitService.retrofit.create(PrivacyDetailApi::class.java)

    val submitExamByUser = retrofitService.retrofit.create(SubmitExamByUser::class.java)

}
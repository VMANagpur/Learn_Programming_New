package com.example.learnprogramming.model

import java.sql.Time

data class TestDetailsResponse(
    val negativeMarking: Int,
    val noOfQuestions: Int,
    val positiveMarking: Int,
    val totalMarks: Int,
    val totalTime: String,
    val mockTestId:Long
)
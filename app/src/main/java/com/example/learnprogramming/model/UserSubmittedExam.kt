package com.example.learnprogramming.model

data class UserSubmittedExam(
    val answers: List<Answer>,
    val paperSetId: Long,
    val userId: Long
)
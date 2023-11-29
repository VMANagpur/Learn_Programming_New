package com.example.learnprogramming.model

data class NoOfQuestionItem(
    val id: Long,
    val question: String,
    val options: List<Option>,
)
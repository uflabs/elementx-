package com.example.model

data class LearnTopic(
    val id: String,
    val title: String,
    val iconName: String,
    val summary: String,
    val content: String,
    val keyTakeaways: List<String>,
    val studentTip: String
)

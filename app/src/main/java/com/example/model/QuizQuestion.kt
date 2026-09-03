package com.example.model

enum class QuizQuestionType(val label: String) {
    IDENTIFY_ELEMENT("Identify Element"),
    IDENTIFY_SYMBOL("Chemical Symbol"),
    IDENTIFY_ATOMIC_NUMBER("Atomic Number"),
    CATEGORY("Category"),
    STATE("State of Matter"),
    RANDOM_CLUES("Mystery Clue")
}

data class QuizQuestion(
    val id: Int,
    val type: QuizQuestionType,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

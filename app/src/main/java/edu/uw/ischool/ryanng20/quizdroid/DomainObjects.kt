package edu.uw.ischool.ryanng20.quizdroid

data class Question(
    val question: String,
    val answers: List<String>,
    val correctAnsIndex: Int
)

data class Topic(
    val title: String,
    val shortDesc: String,
    val longDesc: String,
    val questions: List<Question>
)
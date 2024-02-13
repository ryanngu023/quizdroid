package edu.uw.ischool.ryanng20.quizdroid

import android.app.Application
import android.util.Log

class QuizApp : Application() {
    private val TAG: String = "QuizApp"
    var correctCount: Int = 0
    var totalQuestions: Int = 0
    var questionNumber: Int = 1

    lateinit var topicRepository: TopicRepository
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "QuizApp Loaded")
        topicRepository = TopicRepositoryStorage(this)
    }

    fun updateCorrectCount(num: Int) {
        correctCount = num
    }

    fun updateQuestionNumber(num: Int) {
        questionNumber = num
    }

    fun updateTotalQuestions(num: Int) {
        totalQuestions = num
    }
}
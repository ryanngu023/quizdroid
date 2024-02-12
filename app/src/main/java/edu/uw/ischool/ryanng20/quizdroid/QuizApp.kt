package edu.uw.ischool.ryanng20.quizdroid

import android.app.Application
import android.util.Log
import android.content.Context

class QuizApp : Application() {
    private val TAG: String = "QuizApp"
    private val context: Context = this

    lateinit var topicRepository: TopicRepository
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "QuizApp Loaded")
    }
}
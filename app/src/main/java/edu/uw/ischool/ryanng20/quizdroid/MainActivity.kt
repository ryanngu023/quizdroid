package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var mathBtn: Button
    private lateinit var physicsBtn: Button
    private lateinit var marvelBtn: Button
    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Creating main Activity")
        quizApp = application as QuizApp
        topicRepository = quizApp.topicRepository

        val topicList = topicRepository.getAllTopics();

        mathBtn = findViewById(R.id.math)
        physicsBtn = findViewById(R.id.physics)
        marvelBtn = findViewById(R.id.marvel)

        for(i in 0..<topicList.size) {
            val currTopic = topicList[i]
            when (currTopic.title) {
                "Math" -> mathBtn.setOnClickListener {
                    Log.i(TAG, "Math Clicked")
                    startTopicOverview(currTopic.title, currTopic.longDesc)
                }
                "Physics" -> physicsBtn.setOnClickListener {
                    Log.i(TAG, "Physics Clicked")
                    startTopicOverview(currTopic.title, currTopic.longDesc)
                }
                "Marvel Super Heroes" -> marvelBtn.setOnClickListener {
                    Log.i(TAG, "Marvel Clicked")
                    startTopicOverview(currTopic.title, currTopic.longDesc)
                }
            }
        }
    }

    private fun startTopicOverview(topic: String, desc: String) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("topic", topic)
        intent.putExtra("desc", desc)

        startActivity(intent)
    }

}
package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.util.Log
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

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
        val mathDesc = findViewById<TextView>(R.id.mathsubtext)
        val physicsDesc = findViewById<TextView>(R.id.physicssubtext)
        val marvelDesc = findViewById<TextView>(R.id.marvelsubtext)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mathDesc.text = topicList[0].shortDesc
        physicsDesc.text = topicList[1].shortDesc
        marvelDesc.text = topicList[2].shortDesc

        for(i in 0..<topicList.size) {
            val currTopic = topicList[i]
            when (currTopic.title) {
                "Math" -> mathBtn.setOnClickListener {
                    Log.i(TAG, "Math Clicked")
                    startTopicOverview(currTopic.title)
                }
                "Physics" -> physicsBtn.setOnClickListener {
                    Log.i(TAG, "Physics Clicked")
                    startTopicOverview(currTopic.title)
                }
                "Marvel Super Heroes" -> marvelBtn.setOnClickListener {
                    Log.i(TAG, "Marvel Clicked")
                    startTopicOverview(currTopic.title)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "creating options menu")
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "${item.title} selected")
        val intent = Intent(this, PreferencesActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    private fun startTopicOverview(topic: String) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("topic", topic)

        startActivity(intent)
    }

}
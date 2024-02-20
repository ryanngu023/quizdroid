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
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var firstBtn: Button
    private lateinit var secondBtn: Button
    private lateinit var thirdBtn: Button
    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "Creating main Activity")
        quizApp = application as QuizApp
        topicRepository = quizApp.topicRepository


        firstBtn = findViewById(R.id.firstBtn)
        secondBtn = findViewById(R.id.secondBtn)
        thirdBtn = findViewById(R.id.thirdBtn)
        val firstDesc = findViewById<TextView>(R.id.firstBtnSubtitle)
        val secondDesc = findViewById<TextView>(R.id.secondBtnSubtitle)
        val thirdDesc = findViewById<TextView>(R.id.thirdBtnSubtitle)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val executor: Executor = Executors.newSingleThreadExecutor()
        executor.execute {
            topicRepository.downloadTopics(quizApp.url) {
                if(it) {
                    Log.i(TAG, topicRepository.getAllTopics().toString())
                    runOnUiThread {
                        val topicList = topicRepository.getAllTopics()
                        Log.i(TAG, topicList.toString())
                        firstDesc.text = topicList[0].shortDesc
                        secondDesc.text = topicList[1].shortDesc
                        thirdDesc.text = topicList[2].shortDesc
                        firstBtn.text = topicList[0].title
                        secondBtn.text = topicList[1].title
                        thirdBtn.text = topicList[2].title

                        Log.i(TAG, "Within UI THREAD: $topicList")
                        firstBtn.setOnClickListener {
                            Log.i(TAG, "First Clicked")
                            startTopicOverview(topicList[0].title)
                        }
                        secondBtn.setOnClickListener {
                            Log.i(TAG, "Second Clicked")
                            startTopicOverview(topicList[1].title)
                        }
                        thirdBtn.setOnClickListener {
                            Log.i(TAG, "Third Clicked")
                            startTopicOverview(topicList[2].title)
                        }

                    }
                } else {
                    // default to data/questions.json
                }
            }

        }
        val dllCheck = quizApp.dllCheck
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
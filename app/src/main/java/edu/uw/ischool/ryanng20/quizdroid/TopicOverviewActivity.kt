package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.util.Log
import org.json.JSONObject
import java.io.InputStream


class TopicOverviewActivity : AppCompatActivity() {

    private lateinit var json: JSONObject

    private val TAG: String = "TopicsOverviewActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)
        Log.i(TAG, "Topic Overview Activity")

        val topic = intent.getStringExtra("topic")
        val desc = intent.getStringExtra("desc")
        val topicTitle = findViewById<TextView>(R.id.quiztitle)
        val topicDesc = findViewById<TextView>(R.id.quizdescriptor)
        val topicQuestions = findViewById<TextView>(R.id.questioncount)
        val startBtn = findViewById<Button>(R.id.startquiz)
        topicTitle.text = "$topic Quiz"
        topicDesc.text = desc

        try {
            val inputStream: InputStream = assets.open("quiz_questions.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }
            val json = JSONObject(jsonString)
            val quizTopics = json.getJSONArray("topics")

            for(i in 0..<quizTopics.length()) {
                val item = quizTopics.getJSONObject(i)
                if(item.getString("name") == topic) {
                    topicQuestions.text = "Total Questions: ${item.getJSONArray("questions").length()}"
                }
            }

        } catch (e: Exception) {
            Log.i(TAG, "file read failed")
        }

        startBtn.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("topic", topic)
            intent.putExtra("json", json.toString())
            startActivity(intent)
        }
    }
}
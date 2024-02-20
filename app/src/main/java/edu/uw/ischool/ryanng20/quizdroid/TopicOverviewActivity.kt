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


    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository
    private val TAG: String = "TopicsOverviewActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_overview)
        Log.i(TAG, "Topic Overview Activity")

        val topic = intent.getStringExtra("topic")
        val topicTitle = findViewById<TextView>(R.id.quiztitle)
        val topicDesc = findViewById<TextView>(R.id.quizdescriptor)
        val topicQuestions = findViewById<TextView>(R.id.questioncount)
        val startBtn = findViewById<Button>(R.id.startquiz)
        quizApp = application as QuizApp
        topicRepository = quizApp.topicRepository
        val currTopic = topicRepository.getTopic(topic.toString())
        quizApp.updateTotalQuestions(currTopic!!.questions.size)
        val questionNum = quizApp.totalQuestions
        topicTitle.text = "${currTopic.title} Quiz"
        topicDesc.text = currTopic.longDesc
        topicQuestions.text = "Total Questions: ${questionNum}"



        startBtn.setOnClickListener {
            val intent = Intent(this, QuestionActivity::class.java)
            intent.putExtra("topic", topic)
            startActivity(intent)
        }
    }
}
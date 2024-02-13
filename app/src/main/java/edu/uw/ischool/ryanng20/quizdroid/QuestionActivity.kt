package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream


class QuestionActivity : AppCompatActivity() {

    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository
    private val TAG: String = "QuestionActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        val questionTitle = findViewById<TextView>(R.id.questiontitle)
        val radioGroup = findViewById<RadioGroup>(R.id.answer_radio_group)
        val firstchoice = findViewById<RadioButton>(R.id.first_choice)
        val secondchoice = findViewById<RadioButton>(R.id.second_choice)
        val thirdchoice = findViewById<RadioButton>(R.id.third_choice)
        val fourthchoice = findViewById<RadioButton>(R.id.fourth_choice)
        val submitBtn = findViewById<Button>(R.id.submit)
        quizApp = application as QuizApp
        topicRepository = quizApp.topicRepository
        val topic = intent.getStringExtra("topic")
        Log.i(TAG, "Topic: ${topic}")
        val topicsList = topicRepository.getAllTopics()
        val currTopic = topicsList.find { it.title == topic }
        submitBtn.isEnabled = false;

        val questionsArray = currTopic!!.questions
        val currQuestion = questionsArray[quizApp.questionNumber - 1]
        questionTitle.text = currQuestion.question
        val options = currQuestion.answers
        firstchoice.text = options[0]
        secondchoice.text = options[1]
        thirdchoice.text = options[2]
        fourthchoice.text = options[3]

        var selectedIndex = -1
        radioGroup.setOnCheckedChangeListener { _, selected ->
            submitBtn.isEnabled = selected != -1
            val buttonID = radioGroup.checkedRadioButtonId
            val selectedBtn = radioGroup.findViewById<RadioButton>(buttonID)
            selectedIndex = radioGroup.indexOfChild(selectedBtn)
        }

        submitBtn.setOnClickListener {
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("correct", (selectedIndex == currQuestion.correctAnsIndex))
            intent.putExtra("selected", selectedIndex)
            intent.putExtra("topic", topic)
            startActivity(intent)
        }


    }
}
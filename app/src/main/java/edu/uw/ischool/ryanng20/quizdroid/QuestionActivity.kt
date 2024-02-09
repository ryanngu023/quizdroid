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

    private val TAG: String = "QuestionActivity"
    private lateinit var questionsArray: JSONArray
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
        questionsArray = JSONArray()
        submitBtn.isEnabled = false;
        val topic = intent.getStringExtra("topic")
        Log.i(TAG, "Topic: ${topic}")
        val inputStream: InputStream = assets.open("quiz_questions.json")
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val json = JSONObject(jsonString)
        val quizTopics = json.getJSONArray("topics")
        val numCorrect = intent.getIntExtra("numCorrect", 0)
        val numIncorrect = intent.getIntExtra("incorrect", 0)

        Log.i(TAG, numCorrect.toString())
        Log.i(TAG, numIncorrect.toString())

        for (i in 0..<quizTopics.length()) {
            val item = quizTopics.getJSONObject(i)
            if (item.getString("name") == topic) {
                questionsArray = item.getJSONArray("questions")
                Log.i(TAG, questionsArray.toString())
                break
            }
        }
        val questionNum = intent.getIntExtra("questionNum", 1)

        Log.i(TAG, "At mod: ${questionsArray.toString()}")
        val current = questionsArray.getJSONObject(questionNum - 1)
        Log.i(TAG, current.toString())
        questionTitle.text = current.getString("question")
        val options = current.getJSONArray("answers")
        firstchoice.text = options.getString(0)
        secondchoice.text = options.getString(1)
        thirdchoice.text = options.getString(2)
        fourthchoice.text = options.getString(3)

        var selectedIndex = -1
        radioGroup.setOnCheckedChangeListener { _, selected ->
            submitBtn.isEnabled = selected != -1
            val buttonID = radioGroup.checkedRadioButtonId
            val selectedBtn = radioGroup.findViewById<RadioButton>(buttonID)
            selectedIndex = radioGroup.indexOfChild(selectedBtn)
        }

        submitBtn.setOnClickListener {
            val correctIdx = current.getInt("correctAnswer")
            val intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("correct", (selectedIndex == correctIdx))
            intent.putExtra("selected", options.getString(selectedIndex))
            intent.putExtra("correctAns", options.getString(correctIdx))
            intent.putExtra("questionNum", questionNum)
            intent.putExtra("incorrect", numIncorrect)
            intent.putExtra("numCorrect", numCorrect)
            intent.putExtra("topic", topic)
            startActivity(intent)
        }


    }
}
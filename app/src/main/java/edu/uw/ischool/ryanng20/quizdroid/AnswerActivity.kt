package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView



class AnswerActivity : AppCompatActivity() {

    private val TAG: String = "AnswerActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val correct = intent.getBooleanExtra("correct", true)
        val selected = intent.getStringExtra("selected")
        val correctAns = intent.getStringExtra("correctAns")
        val questionNum = intent.getIntExtra("questionNum", 1)
        var numCorrect = intent.getIntExtra("numCorrect", 0)
        var numIncorrect = intent.getIntExtra("incorrect", 0)
        val topic = intent.getStringExtra("topic")

        val userAnswerText = findViewById<TextView>(R.id.useranswer)
        val correctAnsText = findViewById<TextView>(R.id.correctanswer)
        val numcorrectText = findViewById<TextView>(R.id.numcorrect)
        val nextBtn = findViewById<Button>(R.id.next)

        nextBtn.isEnabled = true

        userAnswerText.text = selected
        correctAnsText.text = correctAns
        Log.i(TAG, "numCorrect: ${numCorrect}")
        Log.i(TAG, "numIncorrect: ${numIncorrect}")
        Log.i(TAG, "QuestionNum: ${questionNum}")
        if(correct) {
            numCorrect += 1
            numcorrectText.text = "You have ${numCorrect} out of ${questionNum + numCorrect - 1 + numIncorrect} correct"
        } else {
            numIncorrect += 1
            numcorrectText.text = "You have ${numCorrect} out of ${questionNum + numCorrect - 1 + numIncorrect} correct"
        }
        Log.i(TAG, "numCorrect after Addition: ${numCorrect}")
        Log.i(TAG, "numIncorrect after Addition: ${numIncorrect}")
        if(questionNum - 1 == 0) {
            nextBtn.text = "Finish"
            nextBtn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            nextBtn.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)
                Log.i(TAG, "On Click Topic: ${topic}")
                intent.putExtra("topic", topic)
                intent.putExtra("questionNum", questionNum - 1)
                intent.putExtra("numCorrect", numCorrect)
                intent.putExtra("incorrect", numIncorrect)
                startActivity(intent)
            }
        }

    }

}
package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class PreferencesActivity : AppCompatActivity() {

    val TAG: String = "PreferencesActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)
        val quizApp = application as QuizApp
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val inputUrl = findViewById<EditText>(R.id.inputURL)
        val inputDuration = findViewById<EditText>(R.id.inputDuration)
        val saveBtn = findViewById<Button>(R.id.saveBtn)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        saveBtn.setOnClickListener {
            val updatedUrl = inputUrl.text.toString()
            val updatedDuration = inputDuration.text.toString()
            if(!updatedUrl.isEmpty() && !updatedDuration.isEmpty()) {
                quizApp.setQuizSettings(updatedUrl, updatedDuration.toInt())
                val intent = Intent(this, MainActivity::class.java)
                Toast.makeText(this, "Settings have been updated", Toast.LENGTH_SHORT).show()
                quizApp.updateQuestionNumber(1)
                quizApp.updateCorrectCount(0)
                quizApp.updateTotalQuestions(0)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please fill both fields with valid inputs.", Toast.LENGTH_SHORT).show()
            }


        }
    }
}
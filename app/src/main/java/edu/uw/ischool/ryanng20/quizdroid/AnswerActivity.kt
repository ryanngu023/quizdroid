package edu.uw.ischool.ryanng20.quizdroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView



class AnswerActivity : AppCompatActivity() {

    private lateinit var quizApp: QuizApp
    private lateinit var topicRepository: TopicRepository
    private val TAG: String = "AnswerActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)

        val correct = intent.getBooleanExtra("correct", true)
        val selected = intent.getIntExtra("selected", 0)
        val topic = intent.getStringExtra("topic")

        quizApp = application as QuizApp
        topicRepository = quizApp.topicRepository
        val topicsList = topicRepository.getAllTopics()
        val currTopic = topicsList.find { it.title == topic }
        val questionsArray = currTopic!!.questions
        val currQuestion = questionsArray[quizApp.questionNumber - 1]

        val userAnswerText = findViewById<TextView>(R.id.useranswer)
        val correctAnsText = findViewById<TextView>(R.id.correctanswer)
        val numcorrectText = findViewById<TextView>(R.id.numcorrect)
        val nextBtn = findViewById<Button>(R.id.next)

        nextBtn.isEnabled = true

        userAnswerText.text = currQuestion.answers[selected]
        Log.i(TAG, currQuestion.correctAnsIndex.toString())
        correctAnsText.text = currQuestion.answers[currQuestion.correctAnsIndex]
        if(correct) {
            quizApp.updateCorrectCount(quizApp.correctCount + 1)
        }
        numcorrectText.text = "You have ${quizApp.correctCount} out of ${quizApp.totalQuestions} correct"
        if(quizApp.questionNumber == quizApp.totalQuestions) {
            nextBtn.text = "Finish"
            nextBtn.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                quizApp.updateQuestionNumber(1)
                quizApp.updateCorrectCount(0)
                quizApp.updateTotalQuestions(0)
                startActivity(intent)
            }
        } else {
            nextBtn.setOnClickListener {
                val intent = Intent(this, QuestionActivity::class.java)
                Log.i(TAG, "On Click Topic: ${topic}")
                intent.putExtra("topic", topic)
                quizApp.updateQuestionNumber(quizApp.questionNumber + 1)
                startActivity(intent)
            }
        }

    }

}
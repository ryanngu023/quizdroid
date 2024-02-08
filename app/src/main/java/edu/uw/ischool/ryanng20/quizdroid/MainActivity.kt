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

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mathBtn = findViewById(R.id.math)
        physicsBtn = findViewById(R.id.physics)
        marvelBtn = findViewById(R.id.marvel)

        mathBtn.setOnClickListener {
            Log.i(TAG, "Math Clicked")
            startTopicOverview("Math",
                "Test your math skills on the math quiz.")
        }
        physicsBtn.setOnClickListener {
            Log.i(TAG, "Physics Clicked")
            startTopicOverview("Physics",
                "Test your physics skills on the physics quiz.")
        }
        marvelBtn.setOnClickListener {
            Log.i(TAG, "Marvel Clicked")
            startTopicOverview("Marvel Super Heroes",
                "Test your knowledge on Marvel on this Marvel Super Heroes Quiz.")
        }
    }

    private fun startTopicOverview(topic: String, desc: String) {
        val intent = Intent(this, TopicOverviewActivity::class.java)
        intent.putExtra("topic", topic)
        intent.putExtra("desc", desc)

        startActivity(intent)
    }

}
package edu.uw.ischool.ryanng20.quizdroid

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
    }
}
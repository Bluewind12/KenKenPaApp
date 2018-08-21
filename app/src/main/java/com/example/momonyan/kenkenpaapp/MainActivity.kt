package com.example.momonyan.kenkenpaapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var startButton: Button
    private lateinit var highScoreText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    private fun init() {
        startButton = findViewById(R.id.startButton1)
        highScoreText = findViewById(R.id.highScoreTextView)
    }
}

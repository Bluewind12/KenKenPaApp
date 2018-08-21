package com.example.momonyan.kenkenpaapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var startButton: Button
    private lateinit var highScoreText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        startButton.setOnClickListener {
            val intent = Intent(this,GameMode::class.java)
            startActivity(intent)
        }

    }

    private fun init() {
        startButton = findViewById(R.id.startButton1)
        highScoreText = findViewById(R.id.highScoreTextView)
    }
}

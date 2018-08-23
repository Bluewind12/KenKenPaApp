package com.example.momonyan.kenkenpaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class EndActivity : AppCompatActivity() {
    private lateinit var scoreText: TextView
    private lateinit var backButton: Button
    private var score: Long = 0
    private var penalty: Int = 0
    private lateinit var data: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_point)
        init()
        setScore()
        scoreText.text = getString(R.string.score, score, penalty)
        backButton.setOnClickListener {
            val endIntent = Intent(this, MainActivity::class.java)
            startActivity(endIntent)
            finish()
        }
    }

    private fun init() {
        scoreText = findViewById(R.id.scoreText)
        backButton = findViewById(R.id.homeBackButton)
        penalty = intent.getIntExtra("penalty", 0)
        score = ((intent.getLongExtra("endTime", 0) - intent.getLongExtra("time", 999999)) / 1000) + penalty * 10
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)
        editor = data.edit()
    }

    private fun setScore() {
        if (score < data.getLong("score", Long.MAX_VALUE)) {
            editor.putLong("score", score)
            editor.apply()
        }
    }
}
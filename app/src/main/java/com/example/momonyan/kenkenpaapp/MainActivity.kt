package com.example.momonyan.kenkenpaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var timeAttackPlayButton: Button
    private lateinit var highScoreText: TextView
    private lateinit var data: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        highScoreText.text = getString(R.string.highScore,data.getLong("score",999))

        //タイムアタック用
        timeAttackPlayButton.setOnClickListener {
            val intent = Intent(this, TimeAttackModeActivity::class.java)
            intent.putExtra("num", 10)
            startActivity(intent)
        }


    }

    private fun init() {
        timeAttackPlayButton = findViewById(R.id.startButton1)
        highScoreText = findViewById(R.id.highScoreTextView)
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)

    }
}

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
    private lateinit var scoreAttackPlayButton: Button
    private lateinit var highTime: TextView
    private lateinit var highScore: TextView
    private lateinit var data: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        highTime.text = getString(R.string.highScore1, data.getLong("scoreTimeA", 999))
        highScore.text = getString(R.string.highScore2, data.getLong("scoreScoreA", 999))

        //タイムアタック用
        timeAttackPlayButton.setOnClickListener {
            val intent = Intent(this, TimeAttackModeActivity::class.java)
            intent.putExtra("num", 10)
            startActivity(intent)
        }
        scoreAttackPlayButton.setOnClickListener {
            val intent = Intent(this, ScoreAttackModeActivity::class.java)
            startActivity(intent)
        }


    }

    private fun init() {
        timeAttackPlayButton = findViewById(R.id.startButton1)
        scoreAttackPlayButton = findViewById(R.id.startButton2)
        highTime = findViewById(R.id.highScore_Time)
        highScore = findViewById(R.id.highScore_Score)
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)

    }
}

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
    private lateinit var resultView: TextView
    private lateinit var backButton: Button

    private var modeInt: Int = 0

    private var penalty: Int = 0

    //ScoreAttack用
    private var scoreSA: Int = 0
    private var scoreAll: Int = 0

    //タイムアタック用
    private var score: Double = 0.0
    private var scorePenaltyAdd: Double = 0.0

    private lateinit var data: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.end_point)
        modeInt = intent.getIntExtra("mode", 0)
        init()
        setScore()
        if (modeInt == 1) {
            scoreText.text = getString(R.string.score, score, penalty)
            resultView.text = getString(R.string.scoreResult, scorePenaltyAdd)
        } else if (modeInt == 2) {
            scoreText.text = getString(R.string.score2, scoreSA, penalty)
            resultView.text = getString(R.string.scoreResult2, scoreAll)
        }
        backButton.setOnClickListener {
            val endIntent = Intent(this, MainActivity::class.java)
            startActivity(endIntent)
            finish()
        }
    }

    private fun init() {
        scoreText = findViewById(R.id.scoreText)
        resultView = findViewById(R.id.resultView)
        backButton = findViewById(R.id.homeBackButton)


        if (modeInt == 1) {
            penalty = intent.getIntExtra("penalty", 0)
            score = (intent.getLongExtra("endTime", 0) - intent.getLongExtra("time", 999999)) / 1000.0
            scorePenaltyAdd = score + (penalty * 10.0)

        } else if (modeInt == 2) {
            penalty = intent.getIntExtra("penalty", 0)
            scoreSA = intent.getIntExtra("score", 0)
            scoreAll = scoreSA - penalty
        }
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)
        editor = data.edit()
    }

    private fun setScore() {
        if (modeInt == 1) {
            if (scorePenaltyAdd < data.getFloat("scoreTimeA", 999.98f)) {
                editor.putFloat("scoreTimeA", scorePenaltyAdd.toFloat())
                editor.apply()
            }
        } else if (modeInt == 2) {
            if (scoreAll > data.getInt("scoreScoreA", 0)) {
                editor.putInt("scoreScoreA", scoreAll)
                editor.apply()
            }

        }
    }
}

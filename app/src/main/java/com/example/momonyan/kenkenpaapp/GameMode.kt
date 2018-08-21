package com.example.momonyan.kenkenpaapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class GameMode : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var buttonL: Button
    private lateinit var buttonC: Button
    private lateinit var buttonR: Button

    //残り回数表示用
    private lateinit var restNumText: TextView

    //ランダムで画像選択のフラグ管理用
    private val random = Random()
    private var randomInt: Int = 0

    //残り回数、管理用
    private var numSet: Int = 999
    //開始時間
    private var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        restNumText.text = getString(R.string.rest, numSet + 1)

        when (randomInt) {
            0 -> image.setImageResource(R.drawable.fabric_mark_circle)
            1 -> image.setImageResource(R.drawable.fabric_mark_triangle)
            2 -> image.setImageResource(R.drawable.roman_number10)
        }

        buttonL.setOnClickListener {
            if (0 == randomInt && 0 != numSet) {
                val nextStageIntent = Intent(this, GameMode::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (0 == randomInt && 0 != numSet) {
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {

            }
        }
        buttonC.setOnClickListener {
            if (1 == randomInt && 0 != numSet) {
                val nextStageIntent = Intent(this, GameMode::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (1 == randomInt && 0 != numSet) {
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {

            }
        }
        buttonR.setOnClickListener {
            if (2 == randomInt && 0 != numSet) {
                val nextStageIntent = Intent(this, GameMode::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (2 == randomInt && 0 != numSet) {
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {

            }
        }
    }

    private fun init() {
        image = findViewById(R.id.imageView)
        buttonL = findViewById(R.id.buttonL)
        buttonC = findViewById(R.id.buttonC)
        buttonR = findViewById(R.id.buttonR)
        restNumText = findViewById(R.id.restNumText)

        randomInt = random.nextInt(3)
        Log.d("RandomInt", randomInt.toString())
        numSet = intent.getIntExtra("num", 9)
        startTime = intent.getLongExtra("time", Calendar.getInstance().timeInMillis)
    }
}
package com.example.momonyan.kenkenpaapp

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class GameModeActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var buttonL: Button
    private lateinit var buttonC: Button
    private lateinit var buttonR: Button

    //残り回数表示用
    private lateinit var restNumText: TextView

    //ランダムで画像選択のフラグ管理用
    private val random = Random()
    private var randomImageInt: Int = 0
    private var randomChoiceInt: Int = 0

    //残り回数、管理用
    private var numSet: Int = 999
    //開始時間
    private var startTime: Long = 0

    // /効果音
    private lateinit var soundPool: SoundPool
    private var success: Int = 0
    private var beep: Int = 0

    //配置管理用
    private var leftChoice: Int = 0
    private var centerChoice: Int = 0
    private var rightChoice: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)
        init()
        restNumText.text = getString(R.string.rest, numSet + 1)

        when (randomImageInt) {
            0 -> image.setImageResource(R.drawable.fabric_mark_circle)
            1 -> image.setImageResource(R.drawable.fabric_mark_triangle)
            2 -> image.setImageResource(R.drawable.roman_number10)
        }
        //数値設定
        choiceNumPoint()

        //ボタンテキスト設定
        buttonTextChange(buttonL,leftChoice)
        buttonTextChange(buttonC,centerChoice)
        buttonTextChange(buttonR,rightChoice)

        buttonL.setOnClickListener {
            if (leftChoice == randomImageInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (leftChoice == randomImageInt && 0 == numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {
                //効果音
                soundPool.play(beep, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
        buttonC.setOnClickListener {
            if (centerChoice == randomImageInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (centerChoice == randomImageInt && 0 == numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)
                //画面遷移
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {
                //効果音
                soundPool.play(beep, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
        buttonR.setOnClickListener {
            if (rightChoice == randomImageInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)
                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (rightChoice == randomImageInt && 0 == numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val finishIntent = Intent(this, EndActivity::class.java)
                finishIntent.putExtra("time", startTime)
                finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
                startActivity(finishIntent)
                finish()
            } else {
                //効果音
                soundPool.play(beep, 1.0f, 1.0f, 0, 0, 1.0f)
            }
        }
    }

    private fun init() {
        image = findViewById(R.id.imageView)
        buttonL = findViewById(R.id.buttonL)
        buttonC = findViewById(R.id.buttonC)
        buttonR = findViewById(R.id.buttonR)
        restNumText = findViewById(R.id.restNumText)

        randomImageInt = random.nextInt(3)
        randomChoiceInt = random.nextInt(6)

        numSet = intent.getIntExtra("num", 10)
        startTime = intent.getLongExtra("time", Calendar.getInstance().timeInMillis)

        soundPool = SoundPool.Builder().build()
        success = soundPool.load(this, R.raw.crrect_answer2, 1)
        beep = soundPool.load(this, R.raw.blip04, 1)
    }

    private fun choiceNumPoint() {
        when (randomChoiceInt) {
            0 -> {
                leftChoice = 0
                centerChoice = 1
                rightChoice = 2
            }
            1 -> {
                leftChoice = 0
                centerChoice = 2
                rightChoice = 1
            }
            2 -> {
                leftChoice = 1
                centerChoice = 0
                rightChoice = 2
            }
            3 -> {
                leftChoice = 1
                centerChoice = 2
                rightChoice = 0
            }
            4 -> {
                leftChoice = 2
                centerChoice = 1
                rightChoice = 0
            }
            5 -> {
                leftChoice = 2
                centerChoice = 0
                rightChoice = 1
            }
        }
    }

    private fun buttonTextChange(button: Button, choiceNum: Int) {
        when (choiceNum) {
            0 -> button.text = getString(R.string.choice1)
            1 -> button.text = getString(R.string.choice2)
            2 -> button.text = getString(R.string.choice3)
        }
    }
}
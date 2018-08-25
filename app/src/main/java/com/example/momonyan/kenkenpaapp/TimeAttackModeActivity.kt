package com.example.momonyan.kenkenpaapp

import android.content.Intent
import android.media.SoundPool
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class TimeAttackModeActivity : AppCompatActivity() {
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

    //不正解回数
    private var penaltyInt: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_attack_game_layout)
        init()
        resetGame()

        buttonL.setOnClickListener {
            correctDecision(leftChoice)
        }
        buttonC.setOnClickListener {
            correctDecision(centerChoice)
        }
        buttonR.setOnClickListener {
            correctDecision(rightChoice)
        }
    }

    private fun init() {
        image = findViewById(R.id.imageView)
        buttonL = findViewById(R.id.buttonL)
        buttonC = findViewById(R.id.buttonC)
        buttonR = findViewById(R.id.buttonR)
        restNumText = findViewById(R.id.restNumText)


        numSet = 10
        startTime = Calendar.getInstance().timeInMillis
        penaltyInt = 0

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

    //動作決定用
    private fun correctDecision(num: Int) {
        if (num == randomImageInt && 0 != numSet) {
            successFunction()
        } else if (num == randomImageInt && 0 == numSet) {
            endGameFunction()
        } else {
            mistakeFunction()
        }
    }


    //正解時の動作
    private fun successFunction() {
        //効果音
        soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)
        numSet--
        resetGame()
    }

    //正解時（回数終了）の動作
    private fun endGameFunction() {
        //効果音
        soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

        //画面遷移
        val finishIntent = Intent(this, EndActivity::class.java)
        finishIntent.putExtra("time", startTime)
        finishIntent.putExtra("endTime", Calendar.getInstance().timeInMillis)
        finishIntent.putExtra("penalty", penaltyInt)
        finishIntent.putExtra("mode", 1)
        startActivity(finishIntent)
        finish()
    }

    //不正解時の動作
    private fun mistakeFunction() {
        //効果音
        soundPool.play(beep, 1.0f, 1.0f, 0, 0, 1.0f)
        penaltyInt++
    }

    private fun resetGame() {
        randomImageInt = random.nextInt(3)
        randomChoiceInt = random.nextInt(6)
        choiceNumPoint()


        when (randomImageInt) {
            0 -> image.setImageResource(R.drawable.fabric_mark_circle)
            1 -> image.setImageResource(R.drawable.fabric_mark_triangle)
            2 -> image.setImageResource(R.drawable.roman_number10)
        }
        //数値設定
        choiceNumPoint()

        //ボタンテキスト設定
        buttonTextChange(buttonL, leftChoice)
        buttonTextChange(buttonC, centerChoice)
        buttonTextChange(buttonR, rightChoice)

        restNumText.text = getString(R.string.rest, numSet + 1)
    }
}
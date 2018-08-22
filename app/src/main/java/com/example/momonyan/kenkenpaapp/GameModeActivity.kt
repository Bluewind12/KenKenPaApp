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
    private var randomInt: Int = 0

    //残り回数、管理用
    private var numSet: Int = 999
    //開始時間
    private var startTime: Long = 0

    // /効果音
    private lateinit var soundPool: SoundPool
    private var success: Int = 0
    private var beep: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_layout)
        init()
        restNumText.text = getString(R.string.rest, numSet + 1)

        when (randomInt) {
            0 -> image.setImageResource(R.drawable.fabric_mark_circle)
            1 -> image.setImageResource(R.drawable.fabric_mark_triangle)
            2 -> image.setImageResource(R.drawable.roman_number10)
        }

        buttonL.setOnClickListener {
            if (0 == randomInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (0 == randomInt && 0 == numSet) {
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
            if (1 == randomInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (1 == randomInt && 0 == numSet) {
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
            if (2 == randomInt && 0 != numSet) {
                //効果音
                soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)
                //画面遷移
                val nextStageIntent = Intent(this, GameModeActivity::class.java)
                numSet--
                nextStageIntent.putExtra("num", numSet)
                nextStageIntent.putExtra("time", startTime)
                startActivity(nextStageIntent)
                finish()
            } else if (2 == randomInt && 0 == numSet) {
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

        randomInt = random.nextInt(3)
        Log.d("RandomInt", randomInt.toString())
        numSet = intent.getIntExtra("num", 10)
        startTime = intent.getLongExtra("time", Calendar.getInstance().timeInMillis)

        soundPool = SoundPool.Builder().build()
        success = soundPool.load(this, R.raw.crrect_answer2, 1)
        beep = soundPool.load(this, R.raw.blip04, 1)
    }
}
package com.example.momonyan.kenkenpaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.TextView
import android.view.KeyEvent.KEYCODE_BACK



class GameStandbyActivity : AppCompatActivity() {

    private var gameMode: Int = 9
    private lateinit var textView: TextView
    private lateinit var countDown:CountDown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standby_layout)

        textView = findViewById(R.id.standbyTimeText)
        gameMode = intent.getIntExtra("standby", 9)

        val countNum: Long = 3500
        val interval: Long = 100

        countDown = CountDown(countNum, interval)
        countDown.start()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            countDown.cancel()
        }
        return super.onKeyDown(keyCode, event)
    }
    /**
     * カウントダウンの動作
     */
    internal inner class CountDown(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            // 完了
            when (gameMode) {
                0 -> {
                    val intent = Intent(this@GameStandbyActivity, TimeAttackModeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                1 -> {
                    val intent = Intent(this@GameStandbyActivity, ScoreAttackModeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else -> error("ボタン設定ミス")
            }
        }

        // インターバルで呼ばれる
        override fun onTick(millisUntilFinished: Long) {
            // 残り時間を分、秒、ミリ秒に分割
            val ss = millisUntilFinished / 1000 % 60;
            if(ss > 0) {
                textView.text = String.format("%d", ss );
            }else{
                textView.setTextColor(ContextCompat.getColor(this@GameStandbyActivity,R.color.colorAccent))
                textView.text = "GO!"
            }
        }
    }
}
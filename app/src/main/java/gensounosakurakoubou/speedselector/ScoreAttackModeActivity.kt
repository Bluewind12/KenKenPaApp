package gensounosakurakoubou.speedselector

import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.media.SoundPool
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.os.CountDownTimer
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.*


class ScoreAttackModeActivity : AppCompatActivity() {

    private lateinit var timeText: TextView
    private lateinit var scoreText: TextView
    private lateinit var image: ImageView

    private lateinit var buttonL: Button
    private lateinit var buttonC: Button
    private lateinit var buttonR: Button

    //ランダムで画像選択のフラグ管理用
    private val random = Random()
    private var randomImageInt: Int = 0
    private var randomChoiceInt: Int = 0

    //残り回数、管理用
    private var pointNum: Int = 0

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

    //カウントダウン管理用
    private lateinit var countDown: CountDown

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.score_attack_game_layout)

        //初期宣言
        init()
        gameReset()
        //数値設定
        choiceNumPoint()

        //ボタンテキスト設定
        buttonTextChange(buttonL, leftChoice)
        buttonTextChange(buttonC, centerChoice)
        buttonTextChange(buttonR, rightChoice)

        val countNum: Long = 15 * 1000
        val interval: Long = 100

        countDown = CountDown(countNum, interval)
        countDown.start()

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

    //キー入力動作
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            countDown.cancel()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun init() {
        image = findViewById(R.id.imageView2)
        buttonL = findViewById(R.id.scoreButtonL)
        buttonC = findViewById(R.id.scoreButtonC)
        buttonR = findViewById(R.id.scoreButtonR)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        buttonL.width = (point.x / 3) - 90
        buttonL.height = (point.y / 10)
        buttonC.width = (point.x / 3) - 90
        buttonC.height = (point.y / 10)
        buttonR.width = (point.x / 3) - 90
        buttonR.height = (point.y / 10)


        timeText = findViewById(R.id.timeTextView)
        scoreText = findViewById(R.id.scoreTextView)
        scoreText.text = getString(R.string.scoreView, 0, 0)

        soundPool = SoundPool.Builder().build()
        success = soundPool.load(this, R.raw.crrect_answer2, 1)
        beep = soundPool.load(this, R.raw.blip04, 1)
    }

    private fun gameReset() {
        randomImageInt = random.nextInt(3)
        randomChoiceInt = random.nextInt(6)
        setRandomImage()
        choiceNumPoint()
        //ボタンテキスト設定
        buttonTextChange(buttonL, leftChoice)
        buttonTextChange(buttonC, centerChoice)
        buttonTextChange(buttonR, rightChoice)
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
        if (num == randomImageInt) {
            successFunction()
        } else {
            mistakeFunction()
        }
    }


    //正解時の動作
    private fun successFunction() {
        //効果音
        soundPool.play(success, 1.0f, 1.0f, 0, 0, 1.0f)

        //動作
        pointNum++
        scoreText.text = getString(R.string.scoreView, pointNum, penaltyInt)
        gameReset()

    }


    //不正解時の動作
    private fun mistakeFunction() {
        //効果音
        soundPool.play(beep, 1.0f, 1.0f, 0, 0, 1.0f)
        penaltyInt++
        scoreText.text = getString(R.string.scoreView, pointNum, penaltyInt)
    }

    /**
     * カウントダウンの動作
     */
    internal inner class CountDown(millisInFuture: Long, countDownInterval: Long) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            // 完了
            val intent = Intent(this@ScoreAttackModeActivity, EndActivity::class.java)
            intent.putExtra("score", pointNum)
            intent.putExtra("penalty", penaltyInt)
            intent.putExtra("mode", 2)
            startActivity(intent)
            finish()
        }

        // インターバルで呼ばれる
        override fun onTick(millisUntilFinished: Long) {
            // 残り時間を分、秒、ミリ秒に分割
            val ss = millisUntilFinished / 1000 % 60
            timeText.text = String.format("%d", ss)

        }
    }

    /**
     * 画像設定
     *  - ランダムイメージ、残り回数に応じたイメージの設定
     */
    private fun setRandomImage() {
        if (pointNum % 2 == 0) {
            when (randomImageInt) {
                0 -> image.setImageResource(R.drawable.fabric_mark_circle)
                1 -> image.setImageResource(R.drawable.fabric_mark_triangle)
                2 -> image.setImageResource(R.drawable.roman_number10)
            }
        } else {
            when (randomImageInt) {
                0 -> image.setImageResource(R.drawable.mark_maru)
                1 -> image.setImageResource(R.drawable.character_sankaku2)
                2 -> image.setImageResource(R.drawable.mark_batsu)
            }
        }
    }
}

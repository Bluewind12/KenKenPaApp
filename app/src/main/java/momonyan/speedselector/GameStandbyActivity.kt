package momonyan.speedselector

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class GameStandbyActivity : AppCompatActivity() {

    private var gameMode: Int = 9
    private lateinit var textView: TextView
    private lateinit var countDown:CountDown

    private lateinit var soundPool: SoundPool
    private var soundShort: Int = 0
    private var soundLong: Int = 0
    private var ss_def: Long = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standby_layout)

        textView = findViewById(R.id.standbyTimeText)
        gameMode = intent.getIntExtra("standby", 9)

        val audioAttributes = AudioAttributes.Builder()
                // USAGE_MEDIA
                // USAGE_GAME
                .setUsage(AudioAttributes.USAGE_GAME)
                // CONTENT_TYPE_MUSIC
                // CONTENT_TYPE_SPEECH, etc.
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .build()

        soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                // ストリーム数に応じて
                .setMaxStreams(2)
                .build()

        // one.wav をロードしておく
        soundShort = soundPool.load(this, R.raw.short_sound, 1)

        // two.wav をロードしておく
        soundLong = soundPool.load(this, R.raw.long_sound, 1)

        soundPool.play(soundShort, 1.0f, 1.0f, 0, 0, 1.0f)


        //カウントダウン
        val countNum: Long = 3300
        val interval: Long = 50

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
            soundPool.play(soundLong, 1.0f, 1.0f, 0, 0, 1.0f)

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
            val ss = millisUntilFinished / 1000 % 60
            if(ss > 0) {
                if (ss < ss_def) {
                    ss_def = ss
                    textView.text = String.format("%d", ss)
                    soundPool.play(soundShort, 1.0f, 1.0f, 0, 0, 1.0f)
                }
            } else {
                if (ss < ss_def) {
                    ss_def = ss
                    soundPool.play(soundShort, 1.0f, 1.0f, 0, 0, 1.0f)
                    textView.text = String.format("%d", ss)
                    textView.setTextColor(ContextCompat.getColor(this@GameStandbyActivity, R.color.colorAccent))
                    textView.text = "GO"
                }
            }
        }
    }
}
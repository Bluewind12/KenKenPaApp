package momonyan.speedselector

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import jp.co.runners.rateorfeedback.RateOrFeedback
import kotlinx.android.synthetic.main.end_point.*

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
            endIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(endIntent)
            finish()
        }

        //レビュー
        RateOrFeedback(this)
                // レビュー用ストアURL
                .setPlayStoreUrl("https://play.google.com/store/apps/details?id=momonyan.speedselector")
                // 改善点・要望の送信先メールアドレス
                .setFeedbackEmail("gensounosakurakikimimi@gmail.com")
                // 一度、評価するか改善点を送信するを選択した場合、それ以降はダイアログが表示されません。
                // この値をインクリメントすることで再度ダイアログが表示されるようになります。
                .setReviewRequestId(0)
                // 前回ダイアログを表示してから次にダイアログを表示してよいタイミングまでの期間です。
                .setIntervalFromPreviousShowing(60 * 60 * 24 * 7)//7日
                // アプリをインストールしてから、ここで指定された期間はダイアログを表示しません。
                .setNotShowTermSecondsFromInstall(60 * 60 * 1)//1時間
                .setAskLikeAppDialogMessage("このアプリはどうですか?")
                .setAskLikeAppDialogPositiveTitle("楽しい！")
                .setAskLikeAppDialogNegativeTitle("そうでもない")
                .setRequestReviewDialogMessage("それはよかった！\nもしよければストアでレビューして頂けないでしょうか？")
                .setRequestReviewDialogPositiveTitle("レビューする!")
                .setRequestReviewDialogNegativeTitle("今はしない")
                .setRequestFeedbackDialogMessage("改善点や要望を送信しますか？")
                .setRequestFeedbackDialogPositiveTitle("送信する!")
                .setRequestFeedbackDialogNegativeTitle("今はしない")
                // 条件次第でダイアログを表示する
                .showIfNeeds()

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
        var frag = false
        if (modeInt == 1) {
            if (scorePenaltyAdd < data.getFloat("scoreTimeA", 999.98f)) {
                editor.putFloat("scoreTimeA", scorePenaltyAdd.toFloat())
                editor.apply()
                frag = true
            }
        } else if (modeInt == 2) {
            if (scoreAll > data.getInt("scoreScoreA", 0)) {
                editor.putInt("scoreScoreA", scoreAll)
                editor.apply()
                frag = true
            }

        }

        if(frag){
            newScoreTextView.text = getString(R.string.newScore)
        }else{
            newScoreTextView.text = ""
        }
    }
}

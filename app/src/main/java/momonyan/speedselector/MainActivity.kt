package momonyan.speedselector

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
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
        //初期宣言
        init()

        highTime.text = getString(R.string.scoreTime, data.getFloat("scoreTimeA", 999.98f))
        highScore.text = getString(R.string.scorePoint, data.getInt("scoreScoreA", 0))
        val intent = Intent(this, GameStandbyActivity::class.java)

        //タイムアタック用
        timeAttackPlayButton.setOnClickListener {
            intent.putExtra("standby", 0)
            startActivity(intent)
        }
        scoreAttackPlayButton.setOnClickListener {
            intent.putExtra("standby", 1)
            startActivity(intent)
        }


    }

    private fun init() {
        //ボタン処理
        timeAttackPlayButton = findViewById(R.id.startButton1)
        scoreAttackPlayButton = findViewById(R.id.startButton2)
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        Log.d("Point", "X:" + point.x)
        Log.d("Point", "Y:" + point.y)
        timeAttackPlayButton.width = (point.x / 2) - 100
        scoreAttackPlayButton.width = (point.x / 2) - 100

        timeAttackPlayButton.height = (point.y / 10)
        scoreAttackPlayButton.height = (point.y / 10)


        highTime = findViewById(R.id.highScore_Time)
        highScore = findViewById(R.id.highScore_Score)
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu1 -> {
                startActivity(Intent(this, readMeActivity::class.java))
                return true
            }
            R.id.action_menu2 -> {
                AlertDialog.Builder(this)
                        .setTitle("Webページを開きます")
                        .setMessage("「プライバシーポリシー」\n「利用素材について」\nのページを開いてもよろしいですか？")
                        .setPositiveButton("はい") { _, _ ->
                            val uri = Uri.parse(getString(R.string.privacy_policy))
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            startActivity(intent)
                        }
                        .setNegativeButton("いいえ", null)
                        .show()
                return true
            }
            R.id.action_menu3 -> {
                AlertDialog.Builder(this)
                        .setTitle("ハイスコアのリセット")
                        .setMessage("ハイスコアをリセットしてもよろしいですか？")
                        .setPositiveButton("はい") { _, _ ->
                            AlertDialog.Builder(this)
                                    .setTitle("ハイスコアのリセット")
                                    .setMessage("本当によろしいですか？")
                                    .setPositiveButton("大丈夫") { _, _ ->
                                        val editor = data.edit()
                                        editor.putFloat("scoreTimeA", 999.98f)
                                        editor.putInt("scoreScoreA", 0)
                                        editor.apply()
                                        highTime.text = getString(R.string.scoreTime, data.getFloat("scoreTimeA", 999.98f))
                                        highScore.text = getString(R.string.scorePoint, data.getInt("scoreScoreA", 0))
                                    }
                                    .setNegativeButton("いいえ", null)
                                    .show()
                        }
                        .setNegativeButton("いいえ", null)
                        .show()
                return true
            }
        }
        return true
    }
}

package com.example.momonyan.kenkenpaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Point
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
        timeAttackPlayButton.width = (displaySize(0) / 2) - 100
        scoreAttackPlayButton.width = (displaySize(0) / 2) - 100

        timeAttackPlayButton.height = (displaySize(0) / 4) - 100
        scoreAttackPlayButton.height = (displaySize(0) / 4) - 100


        highTime = findViewById(R.id.highScore_Time)
        highScore = findViewById(R.id.highScore_Score)
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)

    }

    /**
     * displaySize
     * ディスプレイのサイズを返却してくれる便利なやつ
     * @param select セレクター(0:横幅 width ,1:縦幅 height)
     * @return 幅
     */
    private fun displaySize(select: Int): Int {
        val wm = windowManager
        val disp = wm.defaultDisplay
        val point = Point()
        disp.getSize(point)
        when (select) {
            0 -> return point.x
            1 -> return point.y
            else -> error("サイズ取得エラー")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu1 -> {
                startActivity(Intent(this, readMeActivity::class.java))
                Log.d("ナビゲーションクリック", "説明")
                return true
            }
            R.id.action_menu2 -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy)))
                startActivity(intent)
                Log.d("ナビゲーションクリック", "プライバシーポリシー")
                return true
            }
        }
        return true
    }

}

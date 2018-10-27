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
        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        timeAttackPlayButton.width = (point.x / 2) - 100
        scoreAttackPlayButton.width = (point.x / 2) - 100

        timeAttackPlayButton.height = (point.x / 10) - 100
        scoreAttackPlayButton.height = (point.x / 10) - 100


        highTime = findViewById(R.id.highScore_Time)
        highScore = findViewById(R.id.highScore_Score)
        data = getSharedPreferences("ScoreTable", Context.MODE_PRIVATE)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu1 -> {
                startActivity(Intent(this, readMeActivity::class.java))
                Log.d("ナビゲーションクリック", "説明")
                return true
            }
            R.id.action_menu2 -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.privacy_policy))))
                Log.d("ナビゲーションクリック", "プライバシーポリシー")
                return true
            }
            R.id.action_menu3 -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.use_material))))
                Log.d("ナビゲーションクリック", "利用素材など")
                return true
            }
        }
        return true
    }
}

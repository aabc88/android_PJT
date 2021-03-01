package com.example.game_ver2

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.Window
import android.view.WindowManager
import android.widget.TextView

var display_x = 0
var display_h = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 가로모드고정
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // 풀스크린모드
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)

        val display: Display = windowManager.defaultDisplay
        display_x = display.width
        display_h = display.height

        val play_button = findViewById<TextView>(R.id.game_play)
        val score_text = findViewById<TextView>(R.id.score)
        play_button.setOnClickListener {
           startActivity(Intent(this, Game_Activity::class.java))
        }

        val prefs = getSharedPreferences("game", MODE_PRIVATE)
        score_text.text = ("Highscore : "+ prefs.getInt("highscore", 0))
    }
}
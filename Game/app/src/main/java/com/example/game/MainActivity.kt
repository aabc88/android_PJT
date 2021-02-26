package com.example.game

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Display
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.example.game.databinding.ActivityMainBinding

var w = 0
var h = 0

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 가로모드고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        // 풀스크린모드
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(view)
        //
        val display: Display = windowManager.defaultDisplay
        w = display.width
        h = display.height
        println("width = " + w)
        println("height = " + h)

        binding.btnPlaygame.setOnClickListener {
            val intent = Intent(this, Game_Main::class.java)
            startActivity(intent)
        }



    }
}
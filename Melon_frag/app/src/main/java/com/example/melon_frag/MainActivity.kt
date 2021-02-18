package com.example.melon_frag

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.example.fruitpjt.*
import com.example.melon_frag.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        var binding = ActivityMainBinding.inflate(layoutInflater)
        var view = binding.root

        super.onCreate(savedInstanceState)
        setContentView(view)
        statusbarVisible(true)
        binding.moveToPlayList.setOnClickListener {
            var intent = Intent(this, activity_play_list::class.java)
            startActivity(intent)
        }

        this.window?.apply {
            this.statusBarColor = Color.rgb(39, 39, 39)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        setFrag(1)
        binding.btnBottom1.setImageResource(R.drawable.bottom_select_1)

        binding.btnBottom1.setOnClickListener {
            setFrag(1)
            binding.btnBottom1.setImageResource(R.drawable.bottom_select_1)
            binding.btnBottom2.setImageResource(R.drawable.main_bottom_2)
            binding.btnBottom3.setImageResource(R.drawable.main_bottom_3)
            binding.btnBottom4.setImageResource(R.drawable.main_bottom_4)
            binding.btnBottom5.setImageResource(R.drawable.main_bottom_5)

            statusbarVisible(true)

        }
        binding.btnBottom2.setOnClickListener {
            setFrag(2)
            binding.btnBottom1.setImageResource(R.drawable.main_bottom_1)
            binding.btnBottom2.setImageResource(R.drawable.bottom_select_2)
            binding.btnBottom3.setImageResource(R.drawable.main_bottom_3)
            binding.btnBottom4.setImageResource(R.drawable.main_bottom_4)
            binding.btnBottom5.setImageResource(R.drawable.main_bottom_5)
            statusbarVisible(false)

        }
        binding.btnBottom3.setOnClickListener {
            setFrag(3)
            binding.btnBottom1.setImageResource(R.drawable.main_bottom_1)
            binding.btnBottom2.setImageResource(R.drawable.main_bottom_2)
            binding.btnBottom3.setImageResource(R.drawable.bottom_select_3)
            binding.btnBottom4.setImageResource(R.drawable.main_bottom_4)
            binding.btnBottom5.setImageResource(R.drawable.main_bottom_5)
            statusbarVisible(true)

        }
        binding.btnBottom4.setOnClickListener {
            setFrag(4)
            binding.btnBottom1.setImageResource(R.drawable.main_bottom_1)
            binding.btnBottom2.setImageResource(R.drawable.main_bottom_2)
            binding.btnBottom3.setImageResource(R.drawable.main_bottom_3)
            binding.btnBottom4.setImageResource(R.drawable.bottom_select_4)
            binding.btnBottom5.setImageResource(R.drawable.main_bottom_5)
            statusbarVisible(true)

        }
        binding.btnBottom5.setOnClickListener {
            setFrag(5)
            binding.btnBottom1.setImageResource(R.drawable.main_bottom_1)
            binding.btnBottom2.setImageResource(R.drawable.main_bottom_2)
            binding.btnBottom3.setImageResource(R.drawable.main_bottom_3)
            binding.btnBottom4.setImageResource(R.drawable.main_bottom_4)
            binding.btnBottom5.setImageResource(R.drawable.bottom_select_5)
            statusbarVisible(true)
        }

    }

    fun statusbarVisible(flag: Boolean) {
        if (flag) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setFrag(fragnum: Int) {
        val ft = supportFragmentManager.beginTransaction()
        when (fragnum) {
            1 -> {
                ft.replace(R.id.main_frame, Fragment1()).commit()
            }
            2 -> {
                ft.replace(R.id.main_frame, Fragment2()).commit()
            }
            3 -> {
                ft.replace(R.id.main_frame, Fragment3()).commit()
            }
            4 -> {
                ft.replace(R.id.main_frame, Fragment4()).commit()
            }
            5 -> {
                ft.replace(R.id.main_frame, Fragment5()).commit()
            }
        }
    }
}

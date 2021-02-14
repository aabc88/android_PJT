package com.example.intenttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import com.example.intenttest.databinding.ActivityMainBinding

var DataList = arrayListOf(
        Data(R.drawable.ic_launcher_background, "0", "a", false),
        Data(R.drawable.ic_launcher_background, "1", "b", false),
        Data(R.drawable.ic_launcher_background, "2", "c", false),
        Data(R.drawable.ic_launcher_background, "3", "d", false),
        Data(R.drawable.ic_launcher_background, "4", "e", false),
        Data(R.drawable.ic_launcher_background, "5", "f", false),
        Data(R.drawable.ic_launcher_background, "6", "f", false),
        Data(R.drawable.ic_launcher_background, "7", "f", false),
        Data(R.drawable.ic_launcher_background, "8", "f", false),
        Data(R.drawable.ic_launcher_background, "9", "f", false),
        Data(R.drawable.ic_launcher_background, "10", "f", false),
        Data(R.drawable.ic_launcher_background, "11", "f", false),
        Data(R.drawable.ic_launcher_background, "12", "f", false),
        Data(R.drawable.ic_launcher_background, "13", "f", false)
)

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        binding.listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        binding.listView.adapter = CustomAdapter(this, DataList)
    }


}
package com.example.melon_frag

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.melon_frag.databinding.ActivityPlayListBinding

var DataList = arrayListOf(
    Data(R.drawable.melon_chart_1, "아이유", "Celebrity", false),
)

class activity_play_list : AppCompatActivity() {
    private lateinit var binding: ActivityPlayListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListBinding.inflate(layoutInflater)
        val view = binding.root
        val adapter = CustomAdapter(this, DataList)

        setContentView(view)

        this.window?.apply {
            this.statusBarColor = Color.parseColor("#272727")
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
        }

        binding.listView.isNestedScrollingEnabled = true
        binding.listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        binding.listView.adapter = CustomAdapter(this, DataList)

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            var checkBox: Boolean = !DataList[position].checked_box
            DataList[position].checked_box = checkBox
            (binding.listView.adapter as CustomAdapter).notifyDataSetChanged()
        }

        binding.editIntent.setOnClickListener {
            var intent = Intent(this, edit_playlist::class.java)
            startActivity(intent)
        }

        binding.btnPlayListShuffle.setOnClickListener {
            DataList.shuffle()
            (binding.listView.adapter as CustomAdapter).notifyDataSetChanged()
        }

        /*binding.btn1.setOnClickListener(View.OnClickListener {
            val checkedItem = binding.listView.checkedItemPosition
            for (i in 0..DataList.size) {
                if (DataList[i].checked_box == true) {
                    var name = DataList[i].name


                }
            }
            val str = binding.listView.getItemAtPosition(0)


            //DataList.add(Data(R.drawable.ic_launcher_background, "1", "a", false))
        })*/
    }

    override fun onPause() {
        super.onPause()
        for (i in 0..DataList.size - 1) {
            DataList[i].checked_box = false
        }
    }

    override fun onRestart() {
        super.onRestart()
        for (i in 0..DataList.size - 1) {
            DataList[i].checked_box = false
        }
        (binding.listView.adapter as CustomAdapter).notifyDataSetChanged()
    }

}
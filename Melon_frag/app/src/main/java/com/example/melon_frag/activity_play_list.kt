package com.example.melon_frag

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import com.example.melon_frag.databinding.ActivityPlayListBinding

var DataList = arrayListOf(
    Data(R.drawable.melon_chart_1, "Celebrity", "아이유", false),
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

class activity_play_list : AppCompatActivity() {
    private lateinit var binding: ActivityPlayListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayListBinding.inflate(layoutInflater)
        val view = binding.root
        val adapter = CustomAdapter(this, DataList)

        setContentView(view)

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

    override fun onRestart() {
        super.onRestart()
        (binding.listView.adapter as CustomAdapter).notifyDataSetChanged()
    }
}
package com.example.melon_frag

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import com.example.melon_frag.databinding.ActivityEditPlaylistBinding
import com.example.melon_frag.databinding.ActivityPlayListBinding

class edit_playlist : AppCompatActivity() {
    private lateinit var binding: ActivityEditPlaylistBinding
    private val DESELECTION: String = "선택해제"
    private val SELECT_ALL: String = "전체선택"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlaylistBinding.inflate(layoutInflater)
        val view = binding.root
        var isAllChecked: Boolean = false
        setContentView(view)
        val adapter = CustomAdapter(this, DataList)

        binding.editList.isNestedScrollingEnabled = true
        binding.editList.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        binding.editList.adapter = CustomAdapter(this, DataList)

        binding.editList.setOnItemClickListener { parent, view, position, id ->
            var checkBox: Boolean = !DataList[position].checked_box
            DataList[position].checked_box = checkBox
            (binding.editList.adapter as CustomAdapter).notifyDataSetChanged()
        }

        binding.allCheck.setOnClickListener {
            for (data in DataList) {
                data.checked_box = !isAllChecked
            }
            isAllChecked = !isAllChecked
            if (isAllChecked) {
                binding.allCheck.text = DESELECTION
                binding.allCheck.setTextColor(Color.parseColor("#00FF3C"))
            } else {
                binding.allCheck.text = SELECT_ALL
                binding.allCheck.setTextColor(Color.parseColor("#6B6A68"))
            }
            (binding.editList.adapter as CustomAdapter).notifyDataSetChanged()
        }

        binding.editFinish.setOnClickListener {
            for (data in DataList) {
                data.checked_box = false
            }
            finish()
        }

        binding.editDelete.setOnClickListener {
            for (i in DataList.size-1 downTo 0) {
                if (DataList[i].checked_box) {
                    DataList.remove(DataList[i])
                }
            }
            (binding.editList.adapter as CustomAdapter).notifyDataSetChanged()
        }
    }
}
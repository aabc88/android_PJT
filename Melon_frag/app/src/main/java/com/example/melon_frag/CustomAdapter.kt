package com.example.melon_frag

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView

class Data(
    val profile: Int, val name: String,
    val song: String, var checked_box: Boolean
) {
    val title = this.name
    var checked = this.checked_box
}

class CustomAdapter(val context: Context, val DataList: ArrayList<Data>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.custom_listview, null)
        val profile = view.findViewById<ImageView>(R.id.imageView)
        val name = view.findViewById<TextView>(R.id.txt_name)
        val songname = view.findViewById<TextView>(R.id.txt_songname)
        val data = DataList[position]
        val checkebox = view.findViewById<CheckBox>(R.id.ch_box)

        profile.setImageResource(data.profile)
        view.setBackgroundColor(Color.DKGRAY)
        name.text = data.name
        songname.text = data.song
        checkebox.isChecked = DataList[position].checked_box

        checkebox.setOnClickListener {
            val nowState: Boolean = !DataList[position].checked_box
            DataList[position].checked_box = nowState
        }
        return view
    }

    override fun getItem(position: Int) = DataList[position]
    override fun getItemId(position: Int) = 0L
    override fun getCount() = DataList.size

    fun getName(position: Int): String {
        var title = DataList[position].name.toString()
        var checked = DataList[position].checked_box

        return title
    }
}
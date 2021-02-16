package com.example.newlistview

import android.graphics.Color
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contacts.view.*


class ContactsViewHolder(v: View) : RecyclerView.ViewHolder(v) {
    var view : View = v

    fun bind(item : Contacts) {
        view.txt_name.text = item.name
        view.txt_phone_num.text = item.tel
        view.checkbox_check.isChecked = item.checked

        view.checkbox_check.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                Toast.makeText(view.context, "${item.name}\n${item.tel}", Toast.LENGTH_SHORT).show()
                view.setBackgroundColor(Color.rgb(27,27,27))
            } else view.setBackgroundColor(Color.argb(32,24,24,24))
        }



    }
}
package com.example.newlistview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contacts.view.*

class Contacts(val name: String, val tel: String, var checked: Boolean) {
}

class ContactsListAdapter(val itemList: List<Contacts>) :
    RecyclerView.Adapter<ContactsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contacts, parent, false)

        return ContactsViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val item = itemList[position]


        holder.view.checkbox_check.isChecked = itemList[position].checked
        if (itemList[position].checked) holder.view.setBackgroundColor(Color.rgb(27, 27, 27)) else
            holder.view.setBackgroundColor(Color.argb(32, 24, 24, 24))
        holder.view.checkbox_check.setOnClickListener {
            val now_state: Boolean = !itemList[position].checked
            itemList[position].checked = now_state
        }
        holder.apply {
            bind(item)
        }


    }
    override fun getItemCount() = itemList.size

}

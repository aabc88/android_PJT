package com.example.newlistview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.newlistview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val contactList = mutableListOf(
        Contacts("1", "010-0000-0000", false),
        Contacts("2", "010-0000-0001", false),
        Contacts("3", "010-0000-0002", false),
        Contacts("4", "010-0000-0003", false),
        Contacts("5", "010-0000-0004", false),
        Contacts("6", "010-0000-0005", false),
        Contacts("7", "010-0000-0006", false),
        Contacts("8", "010-0000-0007", false),
        Contacts("9", "010-0000-0008", false),
        Contacts("10", "010-0000-0009", false),
        Contacts("11", "010-0000-00010", false)
    )

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = ContactsListAdapter(contactList)
        binding.recycle1.adapter = adapter

        binding.btn1.setOnClickListener {
            var count = adapter.itemCount
            for (i in 0..count-1){
            }
        }


    }
}
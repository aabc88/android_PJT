package com.example.fruitpjt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.melon_frag.databinding.Frag1Binding

class Fragment1 : Fragment()    {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Frag1Binding.inflate(layoutInflater)
        val view = binding.root


        return view
    }
}
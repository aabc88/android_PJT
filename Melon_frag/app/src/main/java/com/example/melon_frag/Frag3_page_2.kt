package com.example.melon_frag

import android.media.AudioTrack
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.melon_frag.databinding.Frag3Pager2Binding

class Frag3_page_2 :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Frag3Pager2Binding.inflate(layoutInflater)
        val view = binding.root


        return view
    }

}
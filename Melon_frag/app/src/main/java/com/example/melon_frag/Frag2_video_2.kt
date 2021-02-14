package com.example.melon_frag

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fruitpjt.Fragment2
import com.example.melon_frag.databinding.Farg2Video2Binding


class Frag2_video_2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Farg2Video2Binding.inflate(layoutInflater)
        val view = binding.root

        val video1 : Uri = Uri.parse("android.resource://com.example.melon_frag/raw/cha")
        binding.frag2Video2.setVideoURI(video1)
        binding.frag2Video2.start()

        return view
    }
}
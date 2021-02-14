package com.example.melon_frag

import android.media.AudioTrack
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.melon_frag.databinding.Frag2Video3Binding

class Frag2_video_3 :Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Frag2Video3Binding.inflate(layoutInflater)
        val view = binding.root

        val video1 : Uri = Uri.parse("android.resource://com.example.melon_frag/raw/espa")
        binding.frag2Video3.setVideoURI(video1)
        binding.frag2Video3.start()

        return view
    }

}
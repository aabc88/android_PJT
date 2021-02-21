package com.example.melon_frag

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.melon_frag.databinding.Frag2Video1Binding
import com.yqritc.scalablevideoview.ScalableType

class Frag2_video_1 : Fragment()   {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Frag2Video1Binding.inflate(layoutInflater)
        val view = binding.root
        val video1 : Uri = Uri.parse("android.resource://com.example.melon_frag/raw/iu")
        val mp : MediaPlayer = MediaPlayer()

        binding.frag2Video1.setVideoURI(video1)
        binding.frag2Video1.start()


        return view
    }
}
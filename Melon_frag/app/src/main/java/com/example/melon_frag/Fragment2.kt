package com.example.fruitpjt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.melon_frag.*
import com.example.melon_frag.databinding.Frag2Binding


class Fragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = Frag2Binding.inflate(layoutInflater)
        val view = binding.root
        var nowpage: Int = 1


        setFrag(1)

        binding.btnFrag2Left1.setOnClickListener {
            if (nowpage == 1) {
                setFrag(3)
                nowpage = 3
            } else if (nowpage == 2) {
                setFrag(1)
                nowpage = 1
            } else if (nowpage == 3) {
                setFrag(2)
                nowpage = 2
            }
        }

        binding.btnFrag2Right1.setOnClickListener {
            if (nowpage == 1) {
                setFrag(2)
                nowpage = 2
            } else if (nowpage == 2) {
                setFrag(3)
                nowpage = 3
            } else if (nowpage == 3) {
                setFrag(1)
                nowpage = 1
            }
        }

        return view
    }

    private fun setFrag(fragnum: Int) {
        val ft = childFragmentManager.beginTransaction()
        when (fragnum) {
            1 -> {
                ft.replace(R.id.frag2_frame, Frag2_video_1()).commit()
            }
            2 -> {
                ft.replace(R.id.frag2_frame, Frag2_video_2()).commit()
            }
            3 -> {
                ft.replace(R.id.frag2_frame, Frag2_video_3()).commit()
            }

        }
    }
}




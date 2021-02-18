package com.example.melon_frag

import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.melon_frag.*
import com.example.melon_frag.databinding.Frag2Binding
import com.google.android.material.animation.AnimationUtils


class Fragment2 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var drawerToggle:Boolean = false
        val binding = Frag2Binding.inflate(layoutInflater)
        val view = binding.root
        var nowpage: Int = 1
        setFrag(1)

        var tranLeft:Animation = android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translate_top)
        var tranRight:Animation = android.view.animation.AnimationUtils.loadAnimation(context,R.anim.translate_bot)



        binding.btnIu.setOnClickListener{
            Toast.makeText(context, "플레이리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            DataList.add(Data(R.drawable.melon_chart_1, "Celebrity", "아이유", false))
        }

        binding.asd.setOnClickListener {
                binding.drawLayout.visibility = View.VISIBLE
                binding.drawLayout.startAnimation(tranLeft)
                binding.asd.visibility = View.GONE
        }

        binding.btnDraw.setOnClickListener {
            binding.drawLayout.visibility = View.GONE
            binding.drawLayout.startAnimation(tranRight)
            binding.asd.visibility = View.VISIBLE
        }

        /*class DrawButtonClickListener : View.OnClickListener {
            override fun onClick(v: View) {
                if (drawerToggle === false) {
                    val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(binding.drawLayout, "translationY", -1400f)
                    objectAnimator.duration = 300 //0.5초에 걸쳐 진행.
                    objectAnimator.start()
                } else {
                    val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(binding.drawLayout, "translationY", 0f)
                    objectAnimator.duration = 300 //0.5초에 걸쳐 진행.
                    objectAnimator.start()
                }
                drawerToggle = !drawerToggle
            }
        }

        binding.btnDraw.setOnClickListener(DrawButtonClickListener())*/

        /*binding.btnDraw.setOnClickListener{
            if (drawerToggle) {
                binding.drawLayout.startAnimation(tranRight)
                binding.drawLayout.visibility= View.GONE
                drawerToggle = !drawerToggle
            }else{
                binding.drawLayout.visibility= View.VISIBLE
                drawerToggle = !drawerToggle
            }
        }*/

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
                ft.replace(R.id.frag2_frame2, Frag2_video_1()).commit()
            }
            2 -> {
                ft.replace(R.id.frag2_frame2, Frag2_video_2()).commit()
            }
            3 -> {
                ft.replace(R.id.frag2_frame2, Frag2_video_3()).commit()
            }

        }
    }


}




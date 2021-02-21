package com.example.melon_frag

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.melon_frag.databinding.Frag2Binding


class Fragment2 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var drawerToggle: Boolean = false
        val binding = Frag2Binding.inflate(layoutInflater)
        val view = binding.root
        var nowpage: Int = 1
        setFrag(1)



        var tranLeft: Animation =
            android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_top)
        var tranRight: Animation =
            android.view.animation.AnimationUtils.loadAnimation(context, R.anim.translate_bot)

        binding.btnIu.setOnClickListener {
            val name: String = "아이유"
            val song: String = "Celebrity"
            val img: Int = R.drawable.melon_chart_1
            isExist(img, song, name)
        }
        binding.btnGyoungseo.setOnClickListener {
            val name: String = "경서"
            val song: String = "밤하늘의 별을(2020)"
            val img: Int = R.drawable.melon_chart_2
            isExist(img, song, name)
        }
        binding.btnBts.setOnClickListener {
            val name: String = "방탄소년단"
            val song: String = "Dynamite"
            val img: Int = R.drawable.melon_chart_3
            isExist(img, song, name)
        }
        binding.btnMirani.setOnClickListener {
            val name: String = "미란이(Mirani), 먼치맨(MUNCHMAN), 쿤디판다(Khundi Panda), 머쉬베놈(MUSHVENOM)"
            val song: String = "VVS (Feat. JUSTHIS) (Prod. GroovyRoom)"
            val img: Int = R.drawable.melon_chart_4
            isExist(img, song, name)
        }
        binding.btnJangbeomjoon.setOnClickListener {
            val name: String = "장범준"
            val song: String = "잠이 오질 않네요"
            val img: Int = R.drawable.melon_chart_5
            isExist(img, song, name)
        }
        binding.btnBlackpink.setOnClickListener {
            val name: String = "BLACKPINK"
            val song: String = "Lovesick Girls"
            val img: Int = R.drawable.melon_chart_6
            isExist(img, song, name)
        }
        binding.btn10cm.setOnClickListener {
            val name: String = "10CM"
            val song: String = "이 밤을 빌려 말해요 (바른연애 길잡이 X 10CM)"
            val img: Int = R.drawable.melon_chart_7
            isExist(img, song, name)
        }
        binding.btnSandeul.setOnClickListener {
            val name: String = "산들"
            val song: String = "취기를 빌려 (취향저격 그녀 X 산들)"
            val img: Int = R.drawable.melon_chart_8
            isExist(img, song, name)
        }
        binding.btnLimchangjeong.setOnClickListener {
            val name: String = "임창정"
            val song: String = "힘든 건 사랑이 아니다"
            val img: Int = R.drawable.melon_chart_9
            isExist(img, song, name)
        }
        binding.btnLimchangjeong.setOnClickListener {
            val name: String = "아이유"
            val song: String = "에잇(Prod. Feat. SUGA of BTS)"
            val img: Int = R.drawable.melon_chart_10
            isExist(img, song, name)
        }
        binding.asd.setOnClickListener {
            binding.drawLayout.visibility = View.VISIBLE
            binding.drawLayout.startAnimation(tranLeft)
            binding.asd.visibility = View.GONE
        }

        binding.btnDraw.setOnClickListener {
            binding.drawLayout.visibility = View.GONE
            binding.drawLayout.startAnimation(tranRight)
            Handler().postDelayed({
                //딜레이 후 시작할 코드 작성
                binding.asd.visibility = View.VISIBLE
            }, 400) // 0.5초 딜레이
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

    fun isExist(img: Int, name: String, songname: String) {
        var i: Int = 0
        while (i <= DataList.size - 1) {
            if (DataList[i].song.toString() == name) {
                Toast.makeText(context, "이미 플레이리스트에 있습니다.", Toast.LENGTH_SHORT).show()
                break
            }
            i++
        }
        if (i == DataList.size) {
            Toast.makeText(context, "플레이리스트에 추가되었습니다.", Toast.LENGTH_SHORT).show()
            DataList.add(Data(img, songname, name, false))
        }
    }

    override fun onPause() {
        super.onPause()
        nowFrag = 2
        nowFrag_img = nowFrag
    }
}




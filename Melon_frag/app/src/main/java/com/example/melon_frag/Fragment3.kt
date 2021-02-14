package com.example.fruitpjt

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.melon_frag.*
import com.example.melon_frag.databinding.Frag3Binding

class Fragment3 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = Frag3Binding.inflate(layoutInflater)
        val view = binding.root
        val fragmentList = listOf(Frag3_page_1(),Frag3_page_2(),Frag3_page_3())
        val adapter = ExViewPagerAdapter(this)

        //
        var dpvalue:Int = 64
        var d : Float = resources.displayMetrics.density
        var margin : Int = (dpvalue * d).toInt()

        //

        adapter.fragments = fragmentList
        binding.viewpager.adapter = adapter
        binding.viewpager.setPageTransformer(MarginPageTransformer(10.dpToPx(resources.displayMetrics)))
        binding.viewpager.apply {
            offscreenPageLimit = 2
        }
        binding.viewpager.setPadding(30.dpToPx(resources.displayMetrics), 0,30.dpToPx(resources.displayMetrics), 0 )


        return view


    }
    fun Int.dpToPx(displayMetrics: DisplayMetrics): Int = (this * displayMetrics.density).toInt()
}
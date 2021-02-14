package com.example.melon_frag

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.fruitpjt.Fragment3

class ExViewPagerAdapter(fa: Fragment3) : FragmentStateAdapter(fa){
    var fragments = listOf<Fragment>()

    override fun getItemCount(): Int  = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments.get(position)
    }
}
package com.dicoding.picodiploma.mytablayout

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

//    override fun createFragment(position: Int): Fragment {
//        var fragment: Fragment? = null
//        when (position) {
//            0 -> fragment = HomeFragment()
//            1 -> fragment = ProfileFragment()
//        }
//        return fragment as Fragment
//    }
//
//    override fun getItemCount(): Int {
//        return 2
//    }

    override fun createFragment(position: Int): Fragment {
        return HomeFragment.newInstance(position + 1)
    }

    override fun getItemCount(): Int {
        return 3
    }
}



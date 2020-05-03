package com.silverstar.sampleapp.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.silverstar.sampleapp.ui.all.AllListFragment
import com.silverstar.sampleapp.ui.liked.LikedListFragment

class ListFragmentPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(
        fragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

    private val fragments: List<Fragment> = listOf(
        AllListFragment(),
        LikedListFragment()
    )

    override fun getItem(position: Int): Fragment = fragments[position]


    override fun getCount(): Int = fragments.size
}
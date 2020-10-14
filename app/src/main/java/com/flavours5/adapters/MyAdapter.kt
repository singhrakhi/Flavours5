package com.flavours5.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.flavours5.ui.category.BlankFragment

//
//class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {
//
//    // this is for fragment tabs
//
//    override fun getItem(position: Int): Fragment? {
//        return BlankFragment.newInstance(id.get(position))
//    }
//    // this counts total number of tabs
//    override fun getCount(): Int {
//        return totalTabs
//    }
//}
package com.ozturksahinyetisir.travelguideapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    /**
     * [addFragment] adds new Fragment with [ViewPagerAdapter]
     * [getCount] gets item size of FragmentList so divide tabLayout equal parts.
     * [getItem] gets item position as Integer index as "0", "1".
     * [getPageTitle] set title of View Pagers.
     */
    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}
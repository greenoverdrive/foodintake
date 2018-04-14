package com.overdrive.foodintake.utils

/**
 * Created by Overdrive on 12.04.2018.
 */
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.overdrive.foodintake.fragments.BreakFragment
import com.overdrive.foodintake.fragments.DinnerFragment
import com.overdrive.foodintake.fragments.LunchFragment
import com.overdrive.foodintake.fragments.SnackFragment

/**
 * Fragment to return the clicked tab.
 */
class PagerAdapter(fm: FragmentManager, internal var mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        when (position) {
            0 -> return BreakFragment()
            1 -> return LunchFragment()
            2 -> return DinnerFragment()
            3 -> return SnackFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}
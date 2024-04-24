package com.example.traveltaipeiapplication.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HomeFragmentPagerAdapter(fm: androidx.fragment.app.FragmentManager, var mPages : Array<Fragment>, var mTitles : Array<String>) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        if (position < 0 || mPages == null || mPages.size <= position) {
            return null as Fragment
        }

        return mPages[position]
    }

    override fun getCount(): Int {
        if (mPages == null)  return 0;
        else  return mPages.size;
    }

    override fun getPageTitle(position : Int) : CharSequence {
        if (position < 0 || mTitles == null || mTitles.size <= position) {
            return null as CharSequence;
        }
        return mTitles[position];
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }

    fun setItem(position : Int, fragment : Fragment) {
        if (position < 0 || mPages == null || mPages.size <= position) {
            return;
        }
        mPages[position] = fragment;
    }
}
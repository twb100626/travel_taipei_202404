package com.example.traveltaipeiapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.traveltaipeiapplication.fragment.ImageFragment
import com.example.traveltaipeiapplication.model.AttractionImage


class AttractionInfoImagesAdapter(fragmentManager : FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private var images : Array<AttractionImage>? = null

    override fun getCount(): Int {
        return images?.size ?: 0
    }

    override fun getItem(position: Int): Fragment {
        var fragment = ImageFragment(images!![position].src)
        return fragment
    }

    fun setImages(images : Array<AttractionImage>?) {
        this.images = images
    }
}
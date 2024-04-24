package com.example.traveltaipeiapplication.model

import android.view.View
import com.example.traveltaipeiapplication.adapter.AttractionInfoImagesAdapter

data class AttractionInfoItem(
        val adapter: AttractionInfoImagesAdapter
) {
    var opentimeText : String? = null
    var addressText : String? = null
    var telText : String? = null
    var urlText : String? = null
    var introductionText : String? = null
    var remindText : String? = null
    var listener : View.OnClickListener? = null

    fun onViewClick(view : View) {
        listener?.onClick(view)
    }
}
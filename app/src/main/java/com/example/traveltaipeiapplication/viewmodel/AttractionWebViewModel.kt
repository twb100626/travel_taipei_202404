package com.example.traveltaipeiapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.traveltaipeiapplication.model.Attraction

class AttractionWebViewModel : ViewModel() {
    private var title : String? = null
    private var url : String? = null
    private var attraction : Attraction? = null
    private var lang = 0

    fun getTitle() : String? {
        return title
    }

    fun setTitle(title : String?) {
        this.title = title
    }

    fun getUrl() : String? {
        return url
    }

    fun setUrl(url : String?) {
        this.url = url
    }

    fun getAttraction() : Attraction? {
        return attraction
    }

    fun setAttraction(attraction : Attraction?) {
        this.attraction = attraction
    }

    fun getLang() : Int {
        return lang
    }

    fun setLang(lang : Int) {
        this.lang = lang
    }
}
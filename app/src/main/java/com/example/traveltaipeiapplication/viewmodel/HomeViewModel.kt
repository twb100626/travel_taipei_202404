package com.example.traveltaipeiapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.traveltaipeiapplication.model.News
import com.example.traveltaipeiapplication.model.Attraction
import com.example.traveltaipeiapplication.model.AttractionResponse
import com.example.traveltaipeiapplication.model.NewsResponse
import com.example.traveltaipeiapplication.repository.ConnectionCallback
import com.example.traveltaipeiapplication.repository.HomeRepository

class HomeViewModel : ViewModel() {
    private var lang : Int = 0
    private var newsPage : Int = 1
    private var newsList : MutableList<News> = mutableListOf<News>()
    private var attractionPage : Int = 1
    private var attractionList : MutableList<Attraction> = mutableListOf<Attraction>()
    val MaxItemCountPerPage = 30
    private var repository : HomeRepository = HomeRepository()

    fun getLang() : Int {
        return lang
    }

    fun setLang(lang : Int) {
        this.lang = lang
    }

    private fun getLangString() : String {
        return when (lang) {
            0 -> "zh-tw"
            1 -> "zh-cn"
            3 -> "ja"
            4 -> "ko"
            5 -> "es"
            6 -> "id"
            7 -> "th"
            8 -> "vi"
            else -> "en"
        }
    }

    fun getNews(callback : NewsCallback?) {
        if (newsPage < 0) return
        repository.getNews(getLangString(), newsPage++, object :
            ConnectionCallback<NewsResponse> {
            override fun onResponse(response: NewsResponse) {
                newsList.addAll(response.data)
                var list = mutableListOf<News>()
                list.addAll(response.data)
                callback?.onResponse(list)
                if (response.data.size < MaxItemCountPerPage) newsPage = -1
            }

            override fun onFailure(e: Exception?) {
                newsPage--
                callback?.onFailure(e)
            }
        })
    }

    fun getAllNews() : List<News> {
        return newsList
    }

    fun getAttractions(callback : AttractionCallback?) {
        if (attractionPage < 0) return
        repository.getAttractions(getLangString(), attractionPage++, object : ConnectionCallback<AttractionResponse> {
            override fun onResponse(response: AttractionResponse) {
                attractionList.addAll(response.data)
                var list = mutableListOf<Attraction>()
                list.addAll(response.data)
                callback?.onResponse(list)
                if (response.data.size < MaxItemCountPerPage) attractionPage = -1
            }

            override fun onFailure(e: Exception?) {
                attractionPage--;
                callback?.onFailure(e)
            }
        })
    }

    fun getAllAttractions() : List<Attraction> {
        return attractionList
    }

    fun clear() {
        newsPage = 1
        newsList.clear()
        attractionPage = 1
        attractionList.clear()
    }

    interface NewsCallback {
        fun onResponse(array: List<News>)
        fun onFailure(e: Exception?)
    }

    interface AttractionCallback {
        fun onResponse(array: List<Attraction>)
        fun onFailure(e: Exception?)
    }

    fun setRepository(repository: HomeRepository) {
        this.repository = repository
    }
}
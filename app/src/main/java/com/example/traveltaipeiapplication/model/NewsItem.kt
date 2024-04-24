package com.example.traveltaipeiapplication.model

class NewsItem {
    var title : String? = null
    var description : String? = null
    var url : String? = null

    constructor(news : News) {
        title = news.title
        description = news.description
        url = news.url
    }
}
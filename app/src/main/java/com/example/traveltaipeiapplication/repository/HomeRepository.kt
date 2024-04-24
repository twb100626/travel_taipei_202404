package com.example.traveltaipeiapplication.repository

//import android.util.Log
import com.example.traveltaipeiapplication.model.AttractionResponse
import com.example.traveltaipeiapplication.model.NewsResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


class HomeRepository {
    private val TAG = "[TravelTaipei]" + HomeRepository::class.java.simpleName
    private val BaseUrl = "https://www.travel.taipei/open-api/"

    fun getNews(lang : String, page : Int, callback : ConnectionCallback<NewsResponse>?) {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        var api_news : API_NEWS = retrofit.create(API_NEWS::class.java)
        var call = api_news.getNews(lang, page)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: retrofit2.Response<ResponseBody>?
            ) {
                if (response != null && response.isSuccessful()) {
                    var sBody = response?.body().string()
                    //Log.d(TAG, "Response body: " + sBody)
                    callback?.onResponse(Gson().fromJson(sBody, NewsResponse::class.java))
                } else {
                    callback?.onFailure(Exception(response?.code().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                callback?.onFailure(Exception(t?.message))
            }
        })
    }

    private interface API_NEWS {
        @Headers("Accept: application/json")
        @GET("{mLang}/Events/News")
        fun getNews(
                @Path(value = "mLang") lang : String,
                @Query(value = "page") page : Int
        ): Call<ResponseBody>
    }

    fun getAttractions(lang : String, page : Int, callback : ConnectionCallback<AttractionResponse>?) {
        var retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        var api_attraction : API_ATTRACTION = retrofit.create(API_ATTRACTION::class.java)
        var call = api_attraction.getNews(lang, page)
        call.enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                    call: Call<ResponseBody>?,
                    response: retrofit2.Response<ResponseBody>?
            ) {
                if (response != null && response.isSuccessful()) {
                    var sBody = response?.body().string()
                    //Log.d(TAG, "Response body: " + sBody)
                    callback?.onResponse(Gson().fromJson(sBody, AttractionResponse::class.java))
                } else {
                    callback?.onFailure(Exception(response?.code().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                callback?.onFailure(Exception(t?.message))
            }
        })
    }

    private interface API_ATTRACTION {
        @Headers("Accept: application/json")
        @GET("{mLang}/Attractions/All")
        fun getNews(
                @Path(value = "mLang") lang : String,
                @Query(value = "page") page : Int
        ): Call<ResponseBody>
    }
}
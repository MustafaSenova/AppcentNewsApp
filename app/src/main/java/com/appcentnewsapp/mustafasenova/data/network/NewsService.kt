package com.appcentnewsapp.mustafasenova.data.network

import com.appcentnewsapp.mustafasenova.data.model.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("v2/everything")
    fun getNews(@Query("q") keyword: String): Call<NewsResponse>?
}
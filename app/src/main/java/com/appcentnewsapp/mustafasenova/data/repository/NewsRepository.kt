package com.appcentnewsapp.mustafasenova.data.repository

import com.appcentnewsapp.mustafasenova.data.model.NewsResponse
import com.appcentnewsapp.mustafasenova.data.network.NewsService
import com.appcentnewsapp.mustafasenova.ui.news.NewsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository(private val newsApiService: NewsService) {

    fun searchNews(query: String, viewModel: NewsViewModel) {
        val call = newsApiService.getNews(query)
        call?.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val newsResponse = response.body()
                    newsResponse?.let {
                        val newsArticles = it.articles
                        viewModel.onNewsFetched(newsArticles)
                    }
                } else {
                    response.errorBody()?.string()
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewModel.onNewsFetchError(t.message)
            }
        })
    }


}
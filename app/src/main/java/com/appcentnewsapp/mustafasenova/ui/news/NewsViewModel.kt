package com.appcentnewsapp.mustafasenova.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.data.network.Retrofit
import com.appcentnewsapp.mustafasenova.data.repository.NewsRepository

class NewsViewModel : ViewModel() {

    private val newsRepository = NewsRepository(Retrofit.newsService)
    private val _newsArticles = MutableLiveData<List<Articles>?>()
    val newsArticles: LiveData<List<Articles>?> = _newsArticles
    private val _errorMessage = MutableLiveData<String?>()

    fun callSearchQuery(query: String) {
        newsRepository.searchNews(query, this)
    }
    fun onNewsFetched(newsArticles: List<Articles?>?) {

        if (newsArticles != null) {
            _newsArticles.value = newsArticles.filterNotNull()

            if (newsArticles.isEmpty()){
                _errorMessage.value = "No news found"
            }
            else {
                _errorMessage.value = null
            }
        }
    }
    fun onNewsFetchError(errorMessage: String?) {
        _errorMessage.value = errorMessage
    }
}
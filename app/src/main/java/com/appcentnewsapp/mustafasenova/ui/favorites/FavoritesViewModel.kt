package com.appcentnewsapp.mustafasenova.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.data.repository.LocalNewsRepository

class FavoritesViewModel : ViewModel() {

    private val localNewsRepository = LocalNewsRepository()
    private val _newsArticles = MutableLiveData<List<Articles>?>()
    val likedNewsList: LiveData<List<Articles>?> = _newsArticles

    fun getLocalNews() {
        localNewsRepository.getLocalNews(this)
    }
    fun onNewsFetched(newsArticles: List<Articles?>?) {
        if (newsArticles != null) {
            _newsArticles.value = newsArticles.filterNotNull()
        }
    }
}
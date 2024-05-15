package com.appcentnewsapp.mustafasenova.data.repository

import com.appcentnewsapp.mustafasenova.ui.favorites.FavoritesViewModel
import com.appcentnewsapp.mustafasenova.data.db.SharedPreferencesManager

class LocalNewsRepository {

    fun getLocalNews(viewModel: FavoritesViewModel) {
        val newsList =  SharedPreferencesManager.getSavedArticles()
        viewModel.onNewsFetched(newsList)
    }
}
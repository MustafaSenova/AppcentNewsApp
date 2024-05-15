package com.appcentnewsapp.mustafasenova.data.db

import android.content.Context
import android.content.SharedPreferences
import com.appcentnewsapp.mustafasenova.AppcentNewsApp.Companion.context
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.utils.Constants.APP_NAME
import com.google.gson.Gson

object SharedPreferencesManager {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    init {
        prepareSharedPreferences()
    }
    private fun prepareSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE)
        sharedPreferencesEditor = sharedPreferences.edit()
    }
    fun putArticle(key: String?, value: Articles?) {
        val gson = Gson()
        val json = gson.toJson(value)
        sharedPreferencesEditor.putString(key, json).apply()
    }
    fun getArticle(key: String?): Articles? {
        val gson = Gson()
        val json = sharedPreferences.getString(key, null)
        return gson.fromJson(json, Articles::class.java)
    }
    fun removeArticle(key: String?) {
        sharedPreferencesEditor.remove(key)
        sharedPreferencesEditor.apply()
    }
    fun isArticleSaved(key: String?): Boolean {
        return getArticle(key) != null
    }
    fun getSavedArticles(): List<Articles> {
        val gson = Gson()
        val savedArticles = mutableListOf<Articles>()
        for ((key, value) in sharedPreferences.all) {
            val articleJson = value as String
            val article = gson.fromJson(articleJson, Articles::class.java)
            savedArticles.add(article)
        }
        return savedArticles
    }
}
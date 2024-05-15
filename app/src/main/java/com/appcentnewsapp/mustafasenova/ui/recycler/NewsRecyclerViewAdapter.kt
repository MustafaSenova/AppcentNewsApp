package com.appcentnewsapp.mustafasenova.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.databinding.ItemNewsBinding

class NewsRecyclerViewAdapter(
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<Articles>?
) : RecyclerView.Adapter<NewsViewHolder>() {

    private var newsList: List<Articles>? = null

    fun setNewsList(newsList: List<Articles>?){
        this.newsList = newsList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, recyclerViewItemClickListener)
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList?.getOrNull(position)
        holder.bind(item)
    }
    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }
}
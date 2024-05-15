package com.appcentnewsapp.mustafasenova.ui.recycler

import androidx.recyclerview.widget.RecyclerView
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.databinding.ItemNewsBinding
import com.squareup.picasso.Picasso

class NewsViewHolder(
    private val binding: ItemNewsBinding,
    private val recyclerViewItemClickListener: RecyclerViewItemClickListener<Articles>?
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Articles?) {
        binding.newsSource.text = item?.title
        binding.articleDescription.text = item?.description
        Picasso
            .get()
            .load(item?.urlToImage)
            .into(binding.articleImage)

        binding.root.setOnClickListener {
            recyclerViewItemClickListener?.onClick(item)
        }
    }
}
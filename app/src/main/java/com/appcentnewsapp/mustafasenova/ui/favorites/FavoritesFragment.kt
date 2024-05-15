package com.appcentnewsapp.mustafasenova.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.databinding.FragmentLikedNewsBinding
import com.appcentnewsapp.mustafasenova.ui.details.DetailsActivity
import com.appcentnewsapp.mustafasenova.ui.recycler.NewsRecyclerViewAdapter
import com.appcentnewsapp.mustafasenova.ui.recycler.RecyclerViewItemClickListener
import com.google.gson.Gson

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var _binding: FragmentLikedNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerAdapter()
        viewModel.getLocalNews()
        observeViewModel()
    }
    override fun onResume() {
        super.onResume()
        viewModel.getLocalNews()
    }
    private fun observeViewModel() {
        viewModel.likedNewsList.observe(viewLifecycleOwner) { newsList ->
            newsListRecyclerViewAdapter.setNewsList(newsList)
        }
    }
    private val recyclerViewItemClickListener = object :
        RecyclerViewItemClickListener<Articles> {
        override fun onClick(item: Articles?) {
            Log.d("NewsFragment", "Clicked item: ${item?.title}")
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            val itemJson = Gson().toJson(item)
            intent.putExtra("clickedItemJson", itemJson)
            startActivity(intent)
        }
    }
    private fun initializeRecyclerAdapter() {
        newsListRecyclerViewAdapter = NewsRecyclerViewAdapter(recyclerViewItemClickListener)
        binding.fvNewsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.fvNewsList.adapter = newsListRecyclerViewAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
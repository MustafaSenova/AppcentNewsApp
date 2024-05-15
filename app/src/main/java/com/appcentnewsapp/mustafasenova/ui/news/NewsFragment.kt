package com.appcentnewsapp.mustafasenova.ui.news

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.databinding.FragmentNewsBinding
import com.appcentnewsapp.mustafasenova.ui.details.DetailsActivity
import com.appcentnewsapp.mustafasenova.ui.recycler.NewsRecyclerViewAdapter
import com.appcentnewsapp.mustafasenova.ui.recycler.RecyclerViewItemClickListener
import com.google.gson.Gson

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsListRecyclerViewAdapter: NewsRecyclerViewAdapter
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val searchDelay: Long = 750
    private val handler = Handler(Looper.getMainLooper())
    private var searchRunnable: Runnable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[NewsViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRecyclerAdapter()
        setEditText()
        observeViewModel()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setEditText(){
        val searchEditText: EditText = binding.searchBar
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchRunnable?.let { handler.removeCallbacks(it) }
            }
            override fun afterTextChanged(s: Editable?) {
                searchRunnable = Runnable {
                    val query = s.toString()
                    if (query.isNotBlank()){
                        viewModel.callSearchQuery(query)
                    }
                }
                handler.postDelayed(searchRunnable!!, searchDelay)
            }
        })
    }
    private fun observeViewModel(){
        viewModel.newsArticles.observe(viewLifecycleOwner) { newsArticles ->
            newsListRecyclerViewAdapter.setNewsList(newsArticles)
        }

    }

    private val recyclerViewItemClickListener = object : RecyclerViewItemClickListener<Articles>{
        override fun onClick(item: Articles?) {
            val intent = Intent(requireContext(), DetailsActivity::class.java)
            val itemJson = Gson().toJson(item)
            intent.putExtra("clickedItemJson", itemJson)
            startActivity(intent)
        }
    }
    private fun initializeRecyclerAdapter(){
        newsListRecyclerViewAdapter = NewsRecyclerViewAdapter(recyclerViewItemClickListener)
        binding.newsList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.newsList.adapter = newsListRecyclerViewAdapter
    }
}
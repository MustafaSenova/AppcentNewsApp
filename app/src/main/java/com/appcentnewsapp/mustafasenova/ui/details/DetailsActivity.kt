package com.appcentnewsapp.mustafasenova.ui.details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appcentnewsapp.mustafasenova.R
import com.appcentnewsapp.mustafasenova.data.model.Articles
import com.appcentnewsapp.mustafasenova.databinding.ActivityNewsDetailsBinding
import com.appcentnewsapp.mustafasenova.ui.webview.WebviewActivity
import com.appcentnewsapp.mustafasenova.data.db.SharedPreferencesManager
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailsActivity : AppCompatActivity() {

    private var _binding: ActivityNewsDetailsBinding? = null
    private val binding get() = _binding!!
    private var articles: Articles? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemJson = intent.getStringExtra("clickedItemJson")
        articles = Gson().fromJson(itemJson, Articles::class.java)

        prepareUI()
        checkLiked()
        likeButtonClickListener()
        sourceButtonClickListener()
        shareButtonClickListener()

        binding.backButton.setOnClickListener {
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun checkLiked(): Boolean{
        if (SharedPreferencesManager.isArticleSaved(articles?.url)) {
            binding.ibFavoritebutton.setImageResource(R.drawable.ic_favorite)
            return true
        }
        else {
            binding.ibFavoritebutton.setImageResource(R.drawable.ic_favorite_border)
            return false
        }
    }
    private fun shareButtonClickListener(){
        binding.ibSharebutton.setOnClickListener{
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, articles?.url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "@string/share_url")
            startActivity(shareIntent)
        }
    }
    private fun sourceButtonClickListener(){
        binding.btnSource.setOnClickListener{
            val intent = intent
            intent.setClass(this, WebviewActivity::class.java)
            intent.putExtra("url", articles?.url)
            startActivity(intent)
        }
    }
    private fun likeButtonClickListener(){
        binding.ibFavoritebutton.setOnClickListener{

            if(checkLiked()){
                SharedPreferencesManager.removeArticle(articles?.url)
                showToast(R.string.removed_from_favorites)
            }
            else{
                SharedPreferencesManager.putArticle(articles?.url, articles)
                showToast(R.string.added_to_favorites)
            }
            checkLiked()
        }
    }
    private fun prepareUI(){
        binding.tvNewsauthor.text = articles?.author
        binding.tvNewscontent.text = articles?.content
        binding.tvDate.text = articles?.publishedAt
        binding.tvNewstitle.text = articles?.title
        Picasso.get().load(articles?.urlToImage).into(binding.ivNewsimage)
    }
    private fun showToast(messageResId: Int) {
        val message = getString(messageResId)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
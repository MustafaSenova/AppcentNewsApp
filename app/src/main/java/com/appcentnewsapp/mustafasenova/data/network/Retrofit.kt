package com.appcentnewsapp.mustafasenova.data.network

import com.appcentnewsapp.mustafasenova.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {

    private lateinit var retrofit: Retrofit
    lateinit var newsService: NewsService

    init {
        retrofitBuilder()
        bindServices()
    }
    private fun retrofitBuilder() {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_API_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun bindServices() {
        newsService = retrofit.create(NewsService::class.java)
    }
    private fun getOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.addInterceptor(createHttpLoggingInterceptor())
        httpClient.addInterceptor(createApiKeyInterceptor())

        return httpClient.build()
    }
    private fun createHttpLoggingInterceptor() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()


        return httpLoggingInterceptor
    }
    private fun createApiKeyInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Authorization", Constants.API_KEY)
                .build()
            chain.proceed(modifiedRequest)
        }
    }
}
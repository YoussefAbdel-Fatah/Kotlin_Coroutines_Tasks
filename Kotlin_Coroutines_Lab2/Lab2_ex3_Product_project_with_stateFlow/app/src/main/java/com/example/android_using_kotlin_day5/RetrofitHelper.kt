package com.example.android_using_kotlin_day5

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://dummyjson.com")
        .build()
    
    val retrofitService : ProductService =
        retrofit.create(ProductService::class.java)
}
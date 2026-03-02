package com.example.android_using_kotlin_day5

import retrofit2.Response
import retrofit2.http.GET
//https://dummyjson.com/products
interface ProductService {

    @GET("/products")
    suspend fun getProducts(): Response<ProductResponse>
}
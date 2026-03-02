package com.example.android_using_kotlin_day5

import android.content.Context

class ProductRepository(context: Context) {

    private val productDao = ProductDataBase.getInstance(context).getProductDao()

    suspend fun getProducts(isNetworkAvailable: Boolean): ProductState {
        return try {
            if (isNetworkAvailable) {
                val response = RetrofitHelper.retrofitService.getProducts()
                val products = response.body()?.products ?: listOf()

                // Cache in Room
                productDao.insertProducts(products)

                ProductState.Success(products)
            } else {
                // Fetch from local DB
                val data = productDao.getAll()
                if (data.isEmpty()) {
                    ProductState.Error("No cached data available")
                } else {
                    ProductState.Success(data)
                }
            }
        } catch (e: Exception) {
            ProductState.Error(e.message ?: "Unknown error")
        }
    }
}

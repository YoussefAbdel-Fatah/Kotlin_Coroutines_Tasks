package com.example.android_using_kotlin_day5

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRepository(context: Context) {

    private val productDao = ProductDataBase.getInstance(context).getProductDao()

    fun getProducts(isNetworkAvailable: Boolean): Flow<ProductState> = flow {
        emit(ProductState.Loading)

        try {
            if (isNetworkAvailable) {
                val response = RetrofitHelper.retrofitService.getProducts()
                val products = response.body()?.products ?: listOf()

                // Cache in Room
                productDao.insertProducts(products)

                emit(ProductState.Success(products))
            } else {
                // Fetch from local DB
                val data = productDao.getAll()
                if (data.isEmpty()) {
                    emit(ProductState.Error("No cached data available"))
                } else {
                    emit(ProductState.Success(data))
                }
            }
        } catch (e: Exception) {
            emit(ProductState.Error(e.message ?: "Unknown error"))
        }
    }
}

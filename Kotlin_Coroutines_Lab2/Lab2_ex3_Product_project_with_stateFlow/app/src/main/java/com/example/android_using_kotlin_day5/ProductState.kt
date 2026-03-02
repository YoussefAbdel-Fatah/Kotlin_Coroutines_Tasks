package com.example.android_using_kotlin_day5

sealed class ProductState {
    object Loading : ProductState()
    data class Success(val products: List<Product>) : ProductState()
    data class Error(val message: String) : ProductState()
}

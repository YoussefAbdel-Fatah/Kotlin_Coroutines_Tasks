package com.example.android_using_kotlin_day5

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDAO {

    @Query("SELECT * FROM products")
    suspend fun getAll(): List <Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
}
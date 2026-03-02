package com.example.android_using_kotlin_day5

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String)

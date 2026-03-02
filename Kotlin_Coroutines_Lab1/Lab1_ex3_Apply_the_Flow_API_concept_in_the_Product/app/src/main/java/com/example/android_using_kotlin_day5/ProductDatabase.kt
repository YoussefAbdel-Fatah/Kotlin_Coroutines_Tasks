package com.example.android_using_kotlin_day5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Product::class), version = 1 )
abstract class ProductDataBase : RoomDatabase() {
        abstract fun getProductDao(): ProductDAO
        companion object{
            @Volatile
            private var INSTANCE: ProductDataBase? = null
            fun getInstance (ctx: Context): ProductDataBase{
                return INSTANCE ?: synchronized(this) {
                    val temp = Room.databaseBuilder(
                        ctx.applicationContext, ProductDataBase::class.java, "products_db")
                        .build()
                    INSTANCE = temp
                    temp
                }
        }
    }
}

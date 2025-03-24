package com.example.mvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm.model.ProdutDetails

@Database(entities = arrayOf(ProdutDetails::class), version = 2)
abstract class database : RoomDatabase() {
    abstract fun getProductsDAO(): ProductsDAO

    companion object {
        @Volatile
        private var instance: database? = null

        fun getInstance(context: Context): database {
            return instance ?: synchronized(this) {
                val dbInstance = Room.databaseBuilder(
                    context.applicationContext, database::class.java, "products_db"
                )
                    .build()
                instance = dbInstance
                dbInstance
            }
        }
    }
}
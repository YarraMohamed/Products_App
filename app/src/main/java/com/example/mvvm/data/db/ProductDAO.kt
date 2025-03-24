package com.example.mvvm.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mvvm.model.ProdutDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductsDAO {

    @Query("SELECT * FROM products_table")
    fun getProducts() : Flow<List<ProdutDetails>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProducts(product: ProdutDetails)

    @Delete
    suspend fun deleteProduct(product: ProdutDetails)
}
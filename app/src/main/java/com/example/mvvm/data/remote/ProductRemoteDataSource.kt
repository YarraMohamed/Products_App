package com.example.mvvm.data.remote

import com.example.mvvm.model.ProdutDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRemoteDataSource(private val service: ProductServices) {
     suspend fun getAllProducts(): Flow<List<ProdutDetails>> {
        val result = service.getALlProducts().products
         return flowOf(result)
    }
}
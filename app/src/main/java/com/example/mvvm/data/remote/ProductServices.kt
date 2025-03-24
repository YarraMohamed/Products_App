package com.example.mvvm.data.remote

import com.example.mvvm.model.Products
import retrofit2.http.GET

interface ProductServices {
    @GET("products")
    suspend fun getALlProducts(): Products
}
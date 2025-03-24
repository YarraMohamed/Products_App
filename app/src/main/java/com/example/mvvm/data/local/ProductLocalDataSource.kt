package com.example.mvvm.data.local

import com.example.mvvm.data.db.ProductsDAO
import com.example.mvvm.model.ProdutDetails
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSource(private val productsDAO: ProductsDAO) {
    suspend fun getFavourites() : Flow<List<ProdutDetails>> {
        return productsDAO.getProducts()
    }
    suspend fun addFav(product: ProdutDetails) {
        return productsDAO.addProducts(product)
    }
    suspend fun deleteFav(product: ProdutDetails) {
        return productsDAO.deleteProduct(product)
    }
}
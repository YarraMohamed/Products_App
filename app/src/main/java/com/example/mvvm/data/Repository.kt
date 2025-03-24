package com.example.mvvm.data

import android.net.ConnectivityManager.NetworkCallback
import androidx.lifecycle.LiveData
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.model.ProdutDetails
import kotlinx.coroutines.flow.Flow

class Repository private constructor(private val productsRemoteDataSource: ProductRemoteDataSource,
                                     private val productLocalDataSource: ProductLocalDataSource) {

    suspend fun getProducts() : Flow<List<ProdutDetails>>{
        return productsRemoteDataSource.getAllProducts()
    }

    suspend fun getFavourites() : Flow<List<ProdutDetails>> {
        return productLocalDataSource.getFavourites()
    }

    suspend fun addFavourite(product : ProdutDetails) {
        return productLocalDataSource.addFav(product)
    }

    suspend fun deleteFavourite(product : ProdutDetails) {
        return productLocalDataSource.deleteFav(product)
    }

    companion object {
        private val repository: Repository? = null

        fun getInstance(
            productsRemoteDataSource: ProductRemoteDataSource,
            productLocalDataSource: ProductLocalDataSource
        ): Repository? {
            if (repository == null) {
                return Repository(productsRemoteDataSource,productLocalDataSource)
            }
            return repository
        }
    }
}
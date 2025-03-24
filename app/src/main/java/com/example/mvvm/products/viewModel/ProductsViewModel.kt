package com.example.mvvm.products.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.Repository
import com.example.mvvm.model.ProdutDetails
import com.example.mvvm.model.Response
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ProductsViewModel(private val repo:Repository) : ViewModel() {

    init {
        getAllProducts()
    }
    private val mutableListOfProducts : MutableLiveData<List<ProdutDetails>?> = MutableLiveData()
    val products : MutableLiveData<List<ProdutDetails>?> = mutableListOfProducts

    private val listOfProducts = MutableStateFlow<Response>(Response.Loading)
    val productsList = listOfProducts.asStateFlow()

    private val mutableMessage : MutableLiveData<String> = MutableLiveData("")
    val message : LiveData<String> = mutableMessage

    fun getAllProducts(){
        viewModelScope.launch {
            try {
                val items = repo.getProducts()
                items
                    .catch { listOfProducts.value=Response.Failure(Throwable("Error in list"))}
                    .collect{
                    listOfProducts.value = Response.Success(it)
                }
            }catch (ex:Exception){
                mutableMessage.postValue("Catched an error")
            }
        }
    }

    fun addToFav(product:ProdutDetails){
        viewModelScope.launch {
            try {
                repo.addFavourite(product)
                mutableMessage.postValue("Added Successfully")
                Log.i("TAG", "addToFav: ")
            }catch (ex:Exception){
                mutableMessage.postValue("Error adding this item")
                Log.i("TAG", "Error: ")
            }
        }
    }

    class ProductsFactory(private val repo: Repository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProductsViewModel(repo) as T
        }
    }
}
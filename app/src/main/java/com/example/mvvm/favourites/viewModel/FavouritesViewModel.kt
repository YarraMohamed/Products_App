package com.example.mvvm.favourites.viewModel

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

class FavouritesViewModel (private val repo: Repository) : ViewModel() {

    init {
        getStoredProducts()
    }
    private val mutableListOfProducts : MutableLiveData<List<ProdutDetails>?> = MutableLiveData()
    val products : MutableLiveData<List<ProdutDetails>?> = mutableListOfProducts

    private val mutableMessage : MutableLiveData<String> = MutableLiveData("")
    val message : LiveData<String> = mutableMessage

    private val listOfFavourites = MutableStateFlow<Response>(Response.Loading)
    val favList = listOfFavourites.asStateFlow()

    fun getStoredProducts(){
        viewModelScope.launch {
            try {
                val items = repo.getFavourites()
                items
                    .catch { listOfFavourites.value=Response.Failure(Throwable("Error"))}
                    .collect{
                    listOfFavourites.value = Response.Success(it)
                }
            }catch (ex:Exception){
                mutableMessage.postValue("Catched an error")
            }
        }
    }

    fun deleteFromFav(product: ProdutDetails){
        viewModelScope.launch {
            try {
                repo.deleteFavourite(product)
                mutableMessage.postValue("Deleted Successfully")
                Log.i("TAG", "deleteFromFav: ")
            }catch (ex:Exception){
                mutableMessage.postValue("Error adding this item")
                Log.i("TAG", "Error: ")
            }
        }
    }

    class ProductsFactory(private val repo: Repository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FavouritesViewModel(repo) as T
        }
    }
}
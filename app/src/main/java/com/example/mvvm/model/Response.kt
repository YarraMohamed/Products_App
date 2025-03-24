package com.example.mvvm.model

sealed class Response {
    object Loading : Response()
    data class Success(val list : List<ProdutDetails>) : Response()
    data class Failure (val err:Throwable) : Response()
}
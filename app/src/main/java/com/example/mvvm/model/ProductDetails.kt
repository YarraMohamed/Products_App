package com.example.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "products_table")
data class ProdutDetails(@PrimaryKey(autoGenerate = true) var id: Int = 1, var title:String, var price:Double, var thumbnail:String)


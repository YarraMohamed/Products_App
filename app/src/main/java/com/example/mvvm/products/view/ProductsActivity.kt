package com.example.mvvm.products.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.mvvm.R
import com.example.mvvm.data.Repository
import com.example.mvvm.data.db.database
import com.example.mvvm.data.local.ProductLocalDataSource
import com.example.mvvm.data.remote.ProductRemoteDataSource
import com.example.mvvm.data.remote.RetrofitHelper
import com.example.mvvm.model.ProdutDetails
import com.example.mvvm.model.Response
import com.example.mvvm.products.viewModel.ProductsViewModel
import kotlinx.coroutines.launch

class ProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val factory = ProductsViewModel.ProductsFactory(Repository.getInstance(
                ProductRemoteDataSource(RetrofitHelper.apiService),
                ProductLocalDataSource(database.getInstance(this@ProductsActivity).getProductsDAO()))!!)
            ProductsScreen(
               ViewModelProvider(this@ProductsActivity,factory).get(ProductsViewModel::class.java)
            )
        }
    }
}

@Composable
private fun ProductsScreen(viewModel: ProductsViewModel) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val productsState = viewModel.productsList.collectAsStateWithLifecycle()

        when (productsState.value) {
            is Response.Loading -> {
                CircularProgressIndicator()
            }
            is Response.Success -> {
                LazyColumn {
                    items((productsState.value as Response.Success).list) { product ->
                        ProductItem(product) {
                            viewModel.addToFav(product)
                            Toast.makeText(context,"Added",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            is Response.Failure -> {
                Text(text = "Error: ${(productsState as Response.Failure).err.message}", color = Color.Red)
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun ProductItem(productDetails: ProdutDetails , action:()->Unit) {
    val context = LocalContext.current
    Row (modifier = Modifier
        .padding(top = 30.dp)
        .border(
            1.dp, colorResource(R.color.black),
            RoundedCornerShape(16.dp)
        )
        .fillMaxWidth()){
        GlideImage(
            model = productDetails.thumbnail,
            contentDescription = "Product Image",
            modifier = Modifier.size(200.dp, 200.dp),
        )
        Column {
            Text(text = productDetails.title ,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(10.dp)
            )
            Text(text="${productDetails.price}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 10.dp)
            )
            Button(onClick = action) {
                Text(stringResource(R.string.add_to_favourite))
            }

        }
    }
}

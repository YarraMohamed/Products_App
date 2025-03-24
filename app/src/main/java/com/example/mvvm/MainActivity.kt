package com.example.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mvvm.favourites.view.FavouritesActivity
import com.example.mvvm.products.view.ProductsActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Preview
@Composable
private fun MainApp() {
    val context = LocalContext.current
    val activity = LocalActivity.current
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.movie), contentDescription = "Movie Picture", modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                val intent = Intent(context, ProductsActivity::class.java)
                context.startActivity(intent) }
            , modifier = Modifier.padding(bottom=10.dp).width(300.dp)) {
            Text(stringResource(R.string.show_products))
        }
        Button(onClick = {
            val intent = Intent(context, FavouritesActivity::class.java)
            context.startActivity(intent)
        } , modifier = Modifier.padding(bottom=10.dp).width(300.dp)) {
            Text(stringResource(R.string.show_favourites))
        }
        Button(onClick = {
            activity?.finish()
        } , modifier = Modifier.padding(bottom=10.dp).width(300.dp)) {
            Text(stringResource(R.string.exit))
        }
    }
}
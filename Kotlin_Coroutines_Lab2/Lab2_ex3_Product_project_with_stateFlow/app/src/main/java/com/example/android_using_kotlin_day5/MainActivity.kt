package com.example.android_using_kotlin_day5

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android_using_kotlin_day5.ui.theme.Android_Using_Kotlin_Day5Theme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: ProductViewModel = viewModel()
            val productState by viewModel.productState.collectAsState()

            Android_Using_Kotlin_Day5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductList(
                        productState = productState,
                        modifier = Modifier.padding(innerPadding),
                        onProductClick = { product ->
                            val intent = Intent(this, DetailsActivity::class.java).apply {
                                putExtra("PRODUCT_TITLE", product.title)
                                putExtra("PRODUCT_DESC", product.description)
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductList(onProductClick: (Product) -> Unit, productState: ProductState, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (productState) {
            is ProductState.Loading -> CircularProgressIndicator()
            is ProductState.Error -> Text("Couldn't load products.")
            is ProductState.Success -> LazyColumn {
                items(productState.products.size) {
                    ProductItem(
                        modifier = Modifier,
                        product = productState.products[it],
                        onProductClick = onProductClick
                    )
                }
            }
        }
    }
}

@Composable
fun ProductItem(onProductClick: (Product) -> Unit, product: Product, modifier: Modifier = Modifier) {
    Surface(
        shadowElevation = 8.dp,
        modifier = Modifier
            .clickable() {
                onProductClick(product)
            }
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            Text(product.title)
            Spacer(Modifier.height(16.dp))
            Text(product.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {}
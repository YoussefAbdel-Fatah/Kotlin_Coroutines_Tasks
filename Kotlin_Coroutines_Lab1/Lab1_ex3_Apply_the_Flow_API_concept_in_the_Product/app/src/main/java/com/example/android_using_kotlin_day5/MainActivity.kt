package com.example.android_using_kotlin_day5

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.android_using_kotlin_day5.ui.theme.Android_Using_Kotlin_Day5Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val TAG = "asd -->"
    lateinit var products: MutableState<List<Product>>
    lateinit var status: MutableState<String>

    override fun onCreate(savedInstanceState: Bundle?) {

        val repository = ProductRepository(this)

        // Collect the Flow from the repository
        lifecycleScope.launch {
            repository.getProducts(isNetworkAvailable(this@MainActivity))
                .flowOn(Dispatchers.IO)     // upstream work (API + Room) runs on IO thread
                .collect { state ->         // collect on Main thread
                    when (state) {
                        is ProductState.Loading -> {
                            status.value = "Loading"
                        }
                        is ProductState.Success -> {
                            products.value = state.products
                            status.value = "Loaded"
                        }
                        is ProductState.Error -> {
                            status.value = "Error"
                        }
                    }
                }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            products = remember { mutableStateOf(listOf()) }
            status = remember { mutableStateOf("Loading") }
            Android_Using_Kotlin_Day5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProductList(
                        status = status.value,
                        modifier = Modifier.padding(innerPadding),
                        products = products.value,
                        onProductClick = { product ->
                            // THE NAVIGATION COMMAND
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

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }
}

@Composable
fun ProductList(onProductClick: (Product) -> Unit, status: String, modifier: Modifier = Modifier, products: List<Product>) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(status) {
            "Loading" -> CircularProgressIndicator()
            "Error" -> Text("Couldn't load products.")
            "Loaded" -> LazyColumn {
                items(products.size) {
                    ProductItem(
                        modifier = Modifier,
                        product =  products[it],
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
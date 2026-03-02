package com.example.kotlincoroutines2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlincoroutines2.ui.theme.KotlinCoroutines2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinCoroutines2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val searchViewModel: SearchViewModel = viewModel()
                    SearchScreen(
                        viewModel = searchViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
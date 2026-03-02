package com.example.android_using_kotlin_day5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android_using_kotlin_day5.ui.theme.Android_Using_Kotlin_Day5Theme

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val title = intent.getStringExtra("PRODUCT_TITLE") ?: "No Title"
        val description = intent.getStringExtra("PRODUCT_DESC") ?: "No Description"

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DetailsScreen(title, description)
        }
    }
}

@Composable
fun DetailsScreen(title: String, description: String) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = description,
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
            )
        }
    }
}
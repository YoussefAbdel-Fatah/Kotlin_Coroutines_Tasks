package com.example.kotlincoroutines2

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchScreen(viewModel: SearchViewModel, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    val names by viewModel.filteredNames.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                viewModel.onSearch(it)
            },
            label = { Text("Search by name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(names) { name ->
                Text(
                    text = name,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
        }
    }
}

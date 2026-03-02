package com.example.kotlincoroutines2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val names = listOf(
        "Ahmed", "ali", "Amira",
        "Mohamed", "mona", "Mahmoud",
        "Sara", "salma", "Saeed",
        "Khaled", "kareem", "Karim",
        "Omar", "osama", "Nour",
        "Hana", "hassan", "Youssef"
    )

    val searchQuery = MutableSharedFlow<String>(replay = 1)

    private val _filteredNames = MutableStateFlow<List<String>>(names)
    val filteredNames: StateFlow<List<String>> = _filteredNames

    init {
        viewModelScope.launch {
            searchQuery.collect { query ->
                _filteredNames.value = if (query.isEmpty()) {
                    names
                } else {
                    names.filter { it.lowercase().startsWith(query.lowercase()) }
                }
            }
        }
    }

    fun onSearch(query: String) {
        viewModelScope.launch {
            searchQuery.emit(query)
        }
    }
}

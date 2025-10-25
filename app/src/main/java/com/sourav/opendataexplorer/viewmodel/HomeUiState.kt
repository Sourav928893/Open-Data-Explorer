
package com.sourav.opendataexplorer.viewmodel

import com.sourav.opendataexplorer.domain.model.Product

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val products: List<Product>, val categories: List<String>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

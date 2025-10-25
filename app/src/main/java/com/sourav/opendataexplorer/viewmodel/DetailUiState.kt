
package com.sourav.opendataexplorer.viewmodel

import com.sourav.opendataexplorer.domain.model.Product

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val products: List<Product>) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

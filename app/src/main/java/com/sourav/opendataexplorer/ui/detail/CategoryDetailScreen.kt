
package com.sourav.opendataexplorer.ui.detail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import com.sourav.opendataexplorer.ui.components.ErrorView
import com.sourav.opendataexplorer.ui.components.ListItemRow
import com.sourav.opendataexplorer.ui.components.ShimmerItem
import com.sourav.opendataexplorer.viewmodel.DetailUiState
import com.sourav.opendataexplorer.viewmodel.DetailViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun CategoryDetailScreen(
    categoryName: String,
    navController: NavController,
    viewModel: DetailViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.fetchProductsByCategory(categoryName)

    when (val state = uiState) {
        is DetailUiState.Loading -> {
            LazyColumn {
                items(10) {
                    ShimmerItem()
                }
            }
        }
        is DetailUiState.Success -> {
            LazyColumn {
                items(state.products) { product ->
                    ListItemRow(title = product.title, subtitle = "$${product.price}") {
                        navController.navigate("product/${product.id}")
                    }
                }
            }
        }
        is DetailUiState.Error -> {
            ErrorView(message = state.message) {
                viewModel.fetchProductsByCategory(categoryName)
            }
        }
    }
}

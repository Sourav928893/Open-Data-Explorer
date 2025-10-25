package com.sourav.opendataexplorer.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sourav.opendataexplorer.ui.components.ErrorView
import com.sourav.opendataexplorer.viewmodel.DetailUiState
import com.sourav.opendataexplorer.viewmodel.DetailViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: DetailViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    viewModel.fetchProductById(productId)

    when (val state = uiState) {
        is DetailUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is DetailUiState.Success -> {
            val product = state.products.first()
            Column(modifier = Modifier.padding(16.dp)) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = product.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$${product.price}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Category: ${product.category}", style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Rating: ${product.rating} (${product.ratingCount} reviews)", style = MaterialTheme.typography.bodySmall)
            }
        }
        is DetailUiState.Error -> {
            ErrorView(message = state.message) {
                viewModel.fetchProductById(productId)
            }
        }
    }
}

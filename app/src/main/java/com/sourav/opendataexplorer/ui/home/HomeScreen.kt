package com.sourav.opendataexplorer.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sourav.opendataexplorer.ui.components.EmptyView
import com.sourav.opendataexplorer.ui.components.ErrorView
import com.sourav.opendataexplorer.ui.components.ListItemRow
import com.sourav.opendataexplorer.ui.components.ShimmerItem
import com.sourav.opendataexplorer.viewmodel.HomeUiState
import com.sourav.opendataexplorer.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = getViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedTab by remember { mutableStateOf(0) }
    val isRefreshing = uiState is HomeUiState.Loading

    val pullRefreshState = rememberPullRefreshState(isRefreshing, { viewModel.fetchHomeData() })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("OpenData Explorer") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedTab) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Products") })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("Categories") })
            }

            Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                when (val state = uiState) {
                    is HomeUiState.Loading -> {
                        LazyColumn {
                            items(10) {
                                ShimmerItem()
                            }
                        }
                    }
                    is HomeUiState.Success -> {
                        if (selectedTab == 0) {
                            if (state.products.isEmpty()) {
                                EmptyView(message = "No products found.")
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(state.products, key = { it.id }, contentType = { "product" }) { product ->
                                        ListItemRow(title = product.title, subtitle = "$${product.price}") {
                                            navController.navigate("product/${product.id}")
                                        }
                                    }
                                }
                            }
                        } else {
                            if (state.categories.isEmpty()) {
                                EmptyView(message = "No categories found.")
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxSize()) {
                                    items(state.categories, key = { it }, contentType = { "category" }) { category ->
                                        ListItemRow(title = category, subtitle = "") {
                                            navController.navigate("category/$category")
                                        }
                                    }
                                }
                            }
                        }
                    }
                    is HomeUiState.Error -> {
                        ErrorView(message = state.message) {
                            viewModel.fetchHomeData()
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = isRefreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

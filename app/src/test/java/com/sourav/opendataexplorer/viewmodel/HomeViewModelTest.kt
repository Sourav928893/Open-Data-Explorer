
package com.sourav.opendataexplorer.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sourav.opendataexplorer.data.repository.AppRepository
import com.sourav.opendataexplorer.domain.model.Product
import io.mockk.every
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: AppRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = HomeViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test successful zipped response updates UI state correctly`() = runTest {
        val products = listOf(Product(1, "Test Product", 10.0, "Description", "Category", "url", 4.5, 100))
        val categories = listOf("Category1", "Category2")

        every { repository.getHomeData() } returns Single.just(Pair(products, categories))

        viewModel.fetchHomeData()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(HomeUiState.Success(products, categories), viewModel.uiState.value)
    }

    @Test
    fun `test error in one API triggers error state`() = runTest {
        val errorMessage = "Error fetching data"
        every { repository.getHomeData() } returns Single.error(Throwable(errorMessage))

        viewModel.fetchHomeData()

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(HomeUiState.Error(errorMessage), viewModel.uiState.value)
    }
}

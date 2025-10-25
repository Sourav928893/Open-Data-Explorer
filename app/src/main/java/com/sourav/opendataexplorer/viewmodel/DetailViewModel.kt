
package com.sourav.opendataexplorer.viewmodel

import androidx.lifecycle.ViewModel
import com.sourav.opendataexplorer.data.repository.AppRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val compositeDisposable = CompositeDisposable()

    fun fetchProductsByCategory(category: String) {
        _uiState.value = DetailUiState.Loading
        compositeDisposable.add(
            repository.getProductsByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.value = DetailUiState.Success(it)
                }, {
                    _uiState.value = DetailUiState.Error(it.message ?: "Unknown error")
                })
        )
    }

    fun fetchProductById(id: Int) {
        _uiState.value = DetailUiState.Loading
        compositeDisposable.add(
            repository.getProductById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.value = DetailUiState.Success(listOf(it))
                }, {
                    _uiState.value = DetailUiState.Error(it.message ?: "Unknown error")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

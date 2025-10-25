
package com.sourav.opendataexplorer.viewmodel

import androidx.lifecycle.ViewModel
import com.sourav.opendataexplorer.data.repository.AppRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchHomeData()
    }

    fun fetchHomeData() {
        _uiState.value = HomeUiState.Loading
        compositeDisposable.add(
            repository.getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _uiState.value = HomeUiState.Success(it.first, it.second)
                }, {
                    _uiState.value = HomeUiState.Error(it.message ?: "Unknown error")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

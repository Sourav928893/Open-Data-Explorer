
package com.sourav.opendataexplorer.di

import com.sourav.opendataexplorer.data.api.ApiService
import com.sourav.opendataexplorer.data.repository.AppRepository
import com.sourav.opendataexplorer.viewmodel.DetailViewModel
import com.sourav.opendataexplorer.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single {
        AppRepository(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        DetailViewModel(get())
    }
}

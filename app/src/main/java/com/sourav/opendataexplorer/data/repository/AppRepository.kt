
package com.sourav.opendataexplorer.data.repository

import com.sourav.opendataexplorer.data.api.ApiService
import com.sourav.opendataexplorer.domain.mappers.toDomain
import com.sourav.opendataexplorer.domain.model.Product
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction

class AppRepository(private val apiService: ApiService) {

    fun getHomeData(): Single<Pair<List<Product>, List<String>>> {
        return Single.zip(
            apiService.getProductsSingle(),
            apiService.getCategoriesSingle(),
            BiFunction { productsDto, categories ->
                Pair(productsDto.map { it.toDomain() }, categories)
            }
        )
    }

    fun getProductsByCategory(category: String): Single<List<Product>> {
        return apiService.getProductsByCategorySingle(category).map {
            it.map { productDto -> productDto.toDomain() }
        }
    }

    fun getProductById(id: Int): Single<Product> {
        return apiService.getProductByIdSingle(id).map { it.toDomain() }
    }
}


package com.sourav.opendataexplorer.data.api

import com.sourav.opendataexplorer.data.api.dto.ProductDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("products")
    fun getProductsSingle(): Single<List<ProductDto>>

    @GET("products/categories")
    fun getCategoriesSingle(): Single<List<String>>

    @GET("products/category/{category}")
    fun getProductsByCategorySingle(@Path("category") category: String): Single<List<ProductDto>>

    @GET("products/{id}")
    fun getProductByIdSingle(@Path("id") id: Int): Single<ProductDto>
}

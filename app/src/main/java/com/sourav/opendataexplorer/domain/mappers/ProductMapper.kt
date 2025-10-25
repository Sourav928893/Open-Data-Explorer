
package com.sourav.opendataexplorer.domain.mappers

import com.sourav.opendataexplorer.data.api.dto.ProductDto
import com.sourav.opendataexplorer.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        imageUrl = image,
        rating = rating.rate,
        ratingCount = rating.count
    )
}

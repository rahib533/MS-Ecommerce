package az.rahibjafar.msproduct.dto

import java.math.BigDecimal
import java.util.UUID

data class CreateProductRequest(
    val name: String,
    val price: BigDecimal,
    val stockCount: Int,
    val categoryId: UUID,
    val description: String? = null,
    val inStock: Boolean = true
){

}

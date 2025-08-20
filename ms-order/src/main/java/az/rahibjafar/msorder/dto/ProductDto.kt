package az.rahibjafar.msorder.dto

import java.math.BigDecimal
import java.util.*

data class ProductDto(
    val id: UUID,
    val name: String,
    val price: BigDecimal,
    val stockCount: Int,
    val description: String? = null,
    val inStock: Boolean = true
){

}

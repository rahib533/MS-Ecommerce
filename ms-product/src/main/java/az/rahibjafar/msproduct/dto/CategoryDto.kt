package az.rahibjafar.msproduct.dto

import java.util.*

data class CategoryDto(
    val id: UUID,
    val name: String,
    val description: String? = null
){

}

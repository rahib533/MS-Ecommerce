package az.rahibjafar.msproduct.dto

data class CreateCategoryRequest(
    val name: String,
    val description: String? = null
){
}

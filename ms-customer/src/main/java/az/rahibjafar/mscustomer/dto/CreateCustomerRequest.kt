package az.rahibjafar.mscustomer.dto

data class CreateCustomerRequest(
    val firstName: String,
    val lastName: String,
    val cif: String,
){

}

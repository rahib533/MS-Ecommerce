package az.rahibjafar.mscustomer.dto

import java.util.*

data class CustomerDto(
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    val cif: String,
)

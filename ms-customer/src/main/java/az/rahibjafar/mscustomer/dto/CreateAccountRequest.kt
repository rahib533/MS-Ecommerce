package az.rahibjafar.mscustomer.dto

import java.math.BigDecimal
import java.util.UUID

data class CreateAccountRequest(
    val accountNumber: String,
    val balance: BigDecimal,
    val customerId: UUID
){

}
package az.rahibjafar.mscustomer.dto

import az.rahibjafar.mscustomer.model.Customer
import java.math.BigDecimal
import java.util.*

data class AccountDto(
    val id: UUID?,
    val accountNumber: String,
    val balance: BigDecimal,
    val customer: Customer
)

package az.rahibjafar.msorder.dto

import java.math.BigDecimal
import java.util.*

data class AccountDto(
    val id: UUID?,
    val accountNumber: String,
    val balance: BigDecimal
)

package az.rahibjafar.mspayment.dto

import java.math.BigDecimal
import java.util.*

data class CreatePaymentRequest(
    val orderId: UUID,
    val fromAccountNumber: String,
    val toAccountNumber: String,
    var totalAmount: BigDecimal
){}

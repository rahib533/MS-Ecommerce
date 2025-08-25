package az.rahibjafar.mspayment.dto

import az.rahibjafar.mspayment.model.PaymentStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class PaymentDto(
    val id: UUID?,
    val orderId: UUID,
    val fromAccountNumber: String,
    val toAccountNumber: String,
    var totalAmount: BigDecimal,
    val createdDate: LocalDateTime,
    var statusCompletedDate: LocalDateTime?,
    var statusFailedDate: LocalDateTime?,
    var status: PaymentStatus = PaymentStatus.PENDING,
){

}

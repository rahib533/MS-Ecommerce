package az.rahibjafar.msorder.dto

import az.rahibjafar.msorder.model.OrderStatus
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

data class OrderDto(
    val id: UUID?,
    val productId: UUID,
    val customerID: UUID,
    val accountNumber: String,
    val count: Int,
    val totalAmount: BigDecimal?,
    val createdDate: LocalDateTime,
    val confirmedDate: LocalDateTime?,
    val cancelledDate: LocalDateTime?,
    val status: OrderStatus,
){
}

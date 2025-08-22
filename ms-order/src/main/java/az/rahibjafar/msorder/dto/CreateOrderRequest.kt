package az.rahibjafar.msorder.dto

import java.util.*

data class CreateOrderRequest(
    val productId: UUID,
    val customerID: UUID,
    val accountId: UUID,
    val count: Int
){
}

package az.rahibjafar.msorder.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    val productId: UUID,
    val customerID: UUID,
    val count: Int,
    var totalAmount: BigDecimal?,
    @CreationTimestamp
    val createdDate: LocalDateTime,
    var confirmedDate: LocalDateTime?,
    var cancelledDate: LocalDateTime?,
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
){
    constructor():this(
        id = null,
        productId = UUID.randomUUID(),
        customerID = UUID.randomUUID(),
        count = 0,
        totalAmount = null,
        createdDate = LocalDateTime.now(),
        confirmedDate = null,
        cancelledDate = null,
        status = OrderStatus.PENDING,
    )

    constructor(
        productId: UUID, customerID: UUID, count: Int) : this(
        null, productId, customerID, count, null, LocalDateTime.now(), null, null, OrderStatus.PENDING
    )
}

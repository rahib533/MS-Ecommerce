package az.rahibjafar.msorder.model

import jakarta.persistence.*
import org.apache.kafka.common.protocol.types.Field.Str
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
    @Column(nullable = false)
    val productId: UUID,
    @Column(nullable = false)
    val customerID: UUID,
    @Column(nullable = false)
    val accountNumber: String,
    @Column(nullable = false)
    val count: Int,
    var totalAmount: BigDecimal?,
    @Column(nullable = false)
    @CreationTimestamp
    val createdDate: LocalDateTime,
    var confirmedDate: LocalDateTime?,
    var cancelledDate: LocalDateTime?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
){
    constructor():this(
        id = null,
        productId = UUID.randomUUID(),
        customerID = UUID.randomUUID(),
        accountNumber = "",
        count = 0,
        totalAmount = null,
        createdDate = LocalDateTime.now(),
        confirmedDate = null,
        cancelledDate = null,
        status = OrderStatus.PENDING,
    )

    constructor(
        productId: UUID, customerID: UUID, accountNumber: String, count: Int) : this(
        null, productId, customerID, accountNumber, count, null, LocalDateTime.now(), null, null, OrderStatus.PENDING
    )
}

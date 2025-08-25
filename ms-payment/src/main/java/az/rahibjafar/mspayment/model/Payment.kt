package az.rahibjafar.mspayment.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    @Column(nullable = false)
    val orderId: UUID,
    @Column(nullable = false)
    val fromAccountNumber: String,
    @Column(nullable = false)
    val toAccountNumber: String,
    @Column(nullable = false)
    val totalAmount: BigDecimal,
    @Column(nullable = false)
    @CreationTimestamp
    val createdDate: LocalDateTime,
    var statusCompletedDate: LocalDateTime?,
    var statusFailedDate: LocalDateTime?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: PaymentStatus = PaymentStatus.PENDING,
){
    constructor():this(
        id = null,
        orderId = UUID.randomUUID(),
        fromAccountNumber = "",
        toAccountNumber = "",
        totalAmount = BigDecimal.ZERO,
        createdDate = LocalDateTime.now(),
        statusCompletedDate = null,
        statusFailedDate = null,
        status = PaymentStatus.PENDING,
    )

    constructor(
        orderId: UUID, fromAccountNumber: String, toAccountNumber: String, totalAmount: BigDecimal) : this(
        null, orderId, fromAccountNumber, toAccountNumber, totalAmount, LocalDateTime.now(), null,
            null, PaymentStatus.PENDING
    )
}

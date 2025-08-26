package az.rahibjafar.mscustomer.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.*

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    @Column(unique = true, nullable = false)
    val accountNumber: String,
    var balance: BigDecimal,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    val customer: Customer
){
    constructor(accountNumber: String, balance: BigDecimal, customer: Customer) : this(
        null,
        accountNumber,
        balance,
        customer
    )

    constructor() : this(
        id = UUID.randomUUID(),
        accountNumber = "",
        balance = BigDecimal.ZERO,
        customer = Customer()
    )
}

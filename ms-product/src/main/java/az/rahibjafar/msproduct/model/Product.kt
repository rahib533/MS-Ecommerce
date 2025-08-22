package az.rahibjafar.msproduct.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    val name: String,
    val price: BigDecimal,
    var stockCount: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,
    val description: String? = null,
    val inStock: Boolean = true
){
    constructor(name: String, price: BigDecimal, stockCount: Int, category: Category, description: String?, inStock: Boolean) : this(
        null,
        name,
        price,
        stockCount,
        category,
        description,
        inStock
    )

    constructor() : this(
        id = UUID.randomUUID(),
        name = UUID.randomUUID().toString(),
        price = BigDecimal.ZERO,
        stockCount = 0,
        category = Category(),
        description = null,
        inStock = false
    )
}
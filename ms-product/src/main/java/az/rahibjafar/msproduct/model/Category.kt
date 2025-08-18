package az.rahibjafar.msproduct.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    val name: String,
    val description: String? = null,
){
    constructor() : this(
        id = null,
        name = "",
        description = null,
    )
    constructor(name: String, description: String?) : this(null, name, description)
}

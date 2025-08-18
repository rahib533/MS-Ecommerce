package az.rahibjafar.mscustomer.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID?,
    val firstName: String,
    val lastName: String,
    @Column(unique = true, nullable = false)
    val cif: String,
){
    constructor(firstName: String, lastName: String, cif: String) : this(
        null,
        firstName,
        lastName,
        cif
    )

    constructor() : this(
        id = UUID.randomUUID(),
        firstName = "",
        lastName = "",
        cif = ""
    )
}

package dev.savcheg.statusservice

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.util.*

@Entity(name = "orders")
data class Order(
        @Id
        @GeneratedValue
        val id: UUID? = null,
        @Column
        val restaurantName: String,
        @ElementCollection
        @Column
        val items: Set<String>,
        @Column
        val status: Status
)

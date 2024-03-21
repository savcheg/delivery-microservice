package dev.savcheg.statusservice.order

import dev.savcheg.statusservice.status.Status

data class OrderDto(val restaurantName: String, val items: Set<String>, val status: Status = Status.CREATED) {
    constructor(orderEntity: Order) : this(orderEntity.restaurantName, orderEntity.items, orderEntity.status)

}


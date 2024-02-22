package dev.savcheg.receiverservice

data class Order(val restaurantName: String, val items: Set<String>)

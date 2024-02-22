package dev.savcheg.statusservice

import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrDefault
import kotlin.jvm.optionals.getOrElse

@Service
class OrderService(val orderRepository: OrderRepository) {
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    fun getAllOrders(): List<OrderDto> =
            orderRepository.findAll().map { OrderDto(it) }.toList()

    fun createOrder(orderDto: OrderDto): Order {
        val save = orderRepository.save(
                Order(
                        restaurantName = orderDto.restaurantName,
                        items = orderDto.items,
                        status = Status.CREATED
                )
        )
        launchTimer(save)
        return save
    }

    private fun launchTimer(save: Order) {
        coroutineScope.launch {
            delay(20000)
            when (save.status) {
                Status.CREATED -> launchTimer(orderRepository.save(save.copy(status = Status.ACTIVATED)))
                Status.ACTIVATED -> orderRepository.save(save.copy(status = Status.DONE))
                else -> return@launch
            }
        }
    }

    fun getStatusById(id: String): String =
            orderRepository.findById(UUID.fromString(id)).get().status.value
}
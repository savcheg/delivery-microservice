package dev.savcheg.statusservice.order

import com.ninjasquad.springmockk.MockkBean
import dev.savcheg.statusservice.status.Status
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private lateinit var orderService: OrderService

    @MockkBean
    private lateinit var orderRepository: OrderRepository

    @Test
    fun testNewOrder() {
        val order: Order = mockk()
        val orderDto = OrderDto("", setOf(""), Status.CREATED)
        every { orderRepository.save(any()) } returns order
        orderService.createOrder(orderDto)
    }
}
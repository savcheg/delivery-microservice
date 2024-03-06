package dev.savcheg.statusservice.mvc

import com.ninjasquad.springmockk.MockkBean
import dev.savcheg.statusservice.order.OrderController
import dev.savcheg.statusservice.order.OrderDto
import dev.savcheg.statusservice.order.OrderService
import dev.savcheg.statusservice.status.Status
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc

@WebMvcTest(OrderController::class)
class WebMVCTest(@Autowired val mockMvc: MockMvc) {
    @MockkBean
    private lateinit var orderService: OrderService

    @Test
    fun getAllOrdersTest_returnsJson() {
        val orders: List<OrderDto> = listOf(OrderDto("rest", setOf("item"), Status.CREATED))

        every { orderService.getAllOrders() } returns orders

        mockMvc.perform(get("/orders/all"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("[{\"restaurantName\":\"rest\",\"items\":[\"item\"],\"status\":\"CREATED\"}]"))
    }
}
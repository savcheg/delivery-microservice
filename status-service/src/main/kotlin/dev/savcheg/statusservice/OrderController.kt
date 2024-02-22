package dev.savcheg.statusservice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(val orderService: OrderService) {
    @GetMapping("/all")
    fun getAllOrders() = ResponseEntity.ok().body(orderService.getAllOrders())

    @PostMapping("/new")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun newOrder(@RequestBody orderDto: OrderDto) = orderService.createOrder(orderDto).id

    @GetMapping("/{id}")
    @ResponseBody
    fun getStatusById(@PathVariable id: String) = orderService.getStatusById(id)
}
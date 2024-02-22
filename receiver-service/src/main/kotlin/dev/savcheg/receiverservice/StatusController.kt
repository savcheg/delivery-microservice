package dev.savcheg.receiverservice

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers

@RestController
@RequestMapping("/receivers")
class StatusController {
    val objectMapper = ObjectMapper()

    var id: String = ""

    @PostMapping("/new_order")
    @ResponseBody
    fun newOrder(@RequestBody order: Order): String {
        val request = HttpRequest.newBuilder()
                .uri(URI("http://status-service:8082/orders/new"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        objectMapper.writeValueAsString(order)
                )).build()

        val newHttpClient = HttpClient.newHttpClient()
        val response: HttpResponse<String> = newHttpClient.send(request, BodyHandlers.ofString())
        id = response.body()
        return id
    }

    @GetMapping("/get_status")
    @ResponseBody
    fun getStatusById(@RequestParam id: String): String {
        val request = HttpRequest.newBuilder()
                .uri(URI("http://status-service:8082/orders/$id"))
                .GET().build()
        val newHttpClient = HttpClient.newHttpClient()
        val response: HttpResponse<String> = newHttpClient.send(request, BodyHandlers.ofString())
        return response.body()
    }
}
package dev.savcheg.statusservice

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthCheckController {
    @GetMapping
    fun health(): String {
        return "Health is ok"
    }
}
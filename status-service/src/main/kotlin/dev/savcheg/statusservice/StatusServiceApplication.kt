package dev.savcheg.statusservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StatusServiceApplication

fun main(args: Array<String>) {
    runApplication<StatusServiceApplication>(*args)
}

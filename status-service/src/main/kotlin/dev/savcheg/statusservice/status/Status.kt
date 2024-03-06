package dev.savcheg.statusservice.status

enum class Status(val value: String) {
    CREATED("Created"),
    ACTIVATED("Activated"),
    DONE("Done"),
    CANCELLED("Cancelled")
}
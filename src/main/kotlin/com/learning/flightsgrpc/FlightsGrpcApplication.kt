package com.learning.flightsgrpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FlightsGrpcApplication

fun main(args: Array<String>) {
    runApplication<FlightsGrpcApplication>(*args)
}

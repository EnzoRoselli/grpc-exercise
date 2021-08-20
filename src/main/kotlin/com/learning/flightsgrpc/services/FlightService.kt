package com.learning.flightsgrpc.services

import com.learning.flightsgrpc.repositories.FlightRepository
import com.proto.flightgrpc.FlightResponseDTO
import org.springframework.stereotype.Service

@Service
class FlightService(private val flightRepository: FlightRepository) {

    fun getFlights(hour: Int, minute: Int, timeOfDay: String): List<FlightResponseDTO> {
        return flightRepository.findByDepartureHourAndDepartureMinute(checkHourUponTimeOfDay(timeOfDay, hour), minute)
            .map {
                FlightResponseDTO {
                    flight = it.flight
                    departureTime = it.departure.toString()
                }
            }
    }

    private fun checkHourUponTimeOfDay(timeOfDay: String, hour: Int) =
        if (timeOfDay.equals("PM", ignoreCase = true)) (hour + 12) else hour
}
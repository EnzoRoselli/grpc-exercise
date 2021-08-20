package com.learning.flightsgrpc.repositories

import com.learning.flightsgrpc.models.entities.Flight
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FlightRepository:JpaRepository<Flight, Long>{

    @Query(value = """SELECT * FROM flights f
            WHERE (ABS(HOUR(departure) - ?1) <= 4)
            OR (ABS(HOUR(departure) - ?1) <= 5) AND (MINUTE(departure) - ?2) >= 0"""
            ,nativeQuery = true)
    fun findByDepartureHourAndDepartureMinute(hour: Int, minute: Int):List<Flight>
}
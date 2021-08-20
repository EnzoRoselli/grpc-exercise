package com.learning.flightsgrpc.models.entities

import java.time.LocalTime
import javax.persistence.*

@Entity
@Table(name = "flights")
data class Flight(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
    val flight : String?,
    val departure: LocalTime?){
}



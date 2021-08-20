package com.learning.flightsgrpc.grpc

import com.learning.flightsgrpc.services.FlightService
import com.proto.flightgrpc.FlightServiceCoroutineGrpc
import com.proto.flightgrpc.FlightsRequest
import com.proto.flightgrpc.FlightsResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class FlightServiceGRPC(private val flightService: FlightService): FlightServiceCoroutineGrpc.FlightServiceImplBase() {

    override suspend fun getFlights(request: FlightsRequest) =
        FlightsResponse { addAllFlightList(flightService.getFlights(request.hour, request.minutes, request.timeOfDay.toString()))  }

}
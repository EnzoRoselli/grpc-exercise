package com.learning.flightsgrpc.grpc

import com.proto.greet.*
import io.grpc.Status
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.toList
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class GreetServiceGRPC: GreetServiceCoroutineGrpc.GreetServiceImplBase() {

    override suspend fun greet(request: GreetRequest) =
        GreetResponse { result = "Hello ${request.greeting.firstName}" }

    override suspend fun greetManyTimes(request: GreetManyTimesRequest, responseChannel: SendChannel<GreetManyTimesResponse>) =
        repeat(4){ responseChannel.send { result = "Hello ${it.plus(1)}: ${request.greeting.firstName}" } }

    override suspend fun longGreet(requestChannel: ReceiveChannel<LongGreetRequest>) =
        LongGreetResponse { result = requestChannel.toList().joinToString { "Hello ${it.greeting.firstName}" } }

    override suspend fun greetEveryone(requestChannel: ReceiveChannel<GreetEveryoneRequest>, responseChannel: SendChannel<GreetEveryoneResponse>) {

        //Send instantly a response message (without closing the communication)
//        requestChannel.consumeEach { responseChannel.send { result = "Hello ${it.greeting.firstName}" } }

        //Waits until all elements are sent by client
        requestChannel.toList().forEach { responseChannel.send { result = "Hello ${it.greeting.firstName}" } }
    }
}

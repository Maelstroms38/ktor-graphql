package com.example

import com.apurebase.kgraphql.GraphQL
import com.example.di.mainModule
import com.example.graphql.dessertSchema
import com.example.graphql.reviewSchema
import com.example.services.DessertService
import com.example.services.ReviewService
import io.ktor.application.*
import org.koin.core.context.startKoin
import org.koin.ktor.ext.modules

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    startKoin {
        modules(mainModule)
    }

    install(GraphQL) {
        val dessertService = DessertService()
        val reviewService = ReviewService()
        playground = true
        schema {
            dessertSchema(dessertService)
            reviewSchema(reviewService)
        }
    }
}
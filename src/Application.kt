package com.example

import com.apurebase.kgraphql.GraphQL
import com.example.di.mainModule
import com.example.graphql.authSchema
import com.example.graphql.dessertSchema
import com.example.graphql.profileSchema
import com.example.graphql.reviewSchema
import com.example.services.AuthService
import com.example.services.DessertService
import com.example.services.ProfileService
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
        val authService = AuthService()
        val dessertService = DessertService()
        val reviewService = ReviewService()
        val profileService = ProfileService()

        playground = true
        context { call ->
            authService.verifyToken(call)?.let { +it }
            +log
            +call
        }

        schema {
            authSchema(authService)
            dessertSchema(dessertService)
            reviewSchema(reviewService)
            profileSchema(profileService)
        }
    }
}
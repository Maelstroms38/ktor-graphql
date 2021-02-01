package com.example

import com.apurebase.kgraphql.GraphQL
import com.example.di.mainModule
import com.example.graphql.dessertSchema
import com.example.services.DessertService
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
        playground = true
        schema {
            dessertSchema(dessertService)
        }
    }
}
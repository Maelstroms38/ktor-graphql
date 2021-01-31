package com.example

import com.apurebase.kgraphql.GraphQL
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(GraphQL) {
        playground = true
        schema {
            query("hello") {
                resolver { -> "World" }
            }
        }
    }
}
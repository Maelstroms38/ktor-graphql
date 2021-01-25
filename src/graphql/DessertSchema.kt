package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.*
import com.example.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {

    inputType<DessertInput>{
        description = "The input of the dessert without the identifier"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                dessertService.getDessert(dessertId)
            } catch(e: Exception) {
                null
            }
        }
    }

    query("desserts") {
        description = "Retrieve desserts page"
        resolver { page: Int?, size: Int? ->
            try {
                dessertService.getDessertsPage(page ?: 0, size ?: 10)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createDessert") {
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.createDessert(dessertInput, userId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateDessert") {
        description = "Updates a dessert"
        resolver { dessertId: String, dessertInput: DessertInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.updateDessert(userId, dessertId, dessertInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteDessert") {
        description = "Deletes a dessert"
        resolver { dessertId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                dessertService.deleteDessert(userId, dessertId)
            } catch(e: Exception) {
                false
            }
        }
    }

}
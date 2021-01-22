package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.Dessert
import com.example.models.DessertInput
import com.example.models.User
import com.example.services.DessertService

fun SchemaBuilder.dessertSchema(dessertService: DessertService) {
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
        description = "Retrieve all desserts"
        resolver { ->
            try {
                dessertService.getDesserts()
            } catch (e: Exception) {
                emptyList<Dessert>()
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

    inputType<DessertInput>{
        description = "The input of the dessert without the identifier"
    }

    type<Dessert>{
        description = "Dessert object with the attributes name, description and imageUrl"
    }
}
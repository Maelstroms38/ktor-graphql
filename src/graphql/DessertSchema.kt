package com.example.graphql

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.Dessert
import com.example.models.DessertInput
import com.example.repository.DessertRepository

fun SchemaBuilder.dessertSchema() {

    val repository = DessertRepository()

    inputType<DessertInput> {
        description = "The input of the dessert without the identifier"
    }

    type<Dessert> {
        description = "Dessert object with attributes name, description and imageUrl"
    }

    query("dessert") {
        resolver { dessertId: String ->
            try {
                repository.getById(dessertId)
            } catch (e: Exception) {
                null
            }
        }
    }

    query("desserts") {
        resolver { ->
            try {
                repository.getAll()
            } catch (e: Exception) {
                emptyList<Dessert>()
            }
        }
    }

    mutation("createDessert") {
        description = "Create a new dessert"
        resolver { dessertInput: DessertInput ->
            try {
                val uid = java.util.UUID.randomUUID().toString()
                val dessert = Dessert(uid, dessertInput.name, dessertInput.description, dessertInput.imageUrl)
                repository.add(dessert)
                dessert
            } catch (e: Exception) {
                null
            }
        }
    }
}
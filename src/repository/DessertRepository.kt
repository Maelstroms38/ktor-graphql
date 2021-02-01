package com.example.repository

import com.example.data.desserts
import com.example.models.Dessert
import kotlin.Exception

class DessertRepository : RepositoryInterface<Dessert> {
    override fun getById(id: String): Dessert {
        return try {
            desserts.find { it.id == id } ?: throw Exception("No dessert with that ID exists")
        } catch(e: Throwable) {
            throw Exception("Cannot find dessert")
        }
    }

    override fun getAll(): List<Dessert> {
        return desserts
    }

    override fun delete(id: String): Boolean {
        return try {
            val dessert = desserts.find { it.id == id } ?: throw Exception("No dessert with that ID exists")
            desserts.remove(dessert)
            true
        } catch(e: Throwable) {
            throw Exception("Cannot find dessert")
        }
    }

    override fun add(entry: Dessert): Dessert {
        desserts.add(entry)
        return entry
    }

    override fun update(entry: Dessert): Dessert {
        return try {
            val dessert = desserts.find { it.id == entry.id }?.apply {
                name = entry.name
                description = entry.description
                imageUrl = entry.imageUrl
            } ?: throw Exception("No dessert with that ID exists")
            dessert
        } catch(e: Throwable) {
            throw Exception("Cannot find dessert")
        }
    }
}
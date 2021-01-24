package com.example.repository

import com.example.models.Dessert
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*
import java.lang.Exception

class DessertRepository(client: MongoClient) : RepositoryInterface<Dessert> {
    private val col: MongoCollection<Dessert>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Dessert>("Dessert")
    }

    override fun getById(id: String): Dessert {
        return try {
            col.findOne(Dessert::id eq id)
                ?: throw Exception("No dessert with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get dessert")
        }
    }

    fun getDessertsPage(page: Int, size: Int): List<Dessert> {
        try {
            val skips = size * page
            val res = col.find().limit(size).skip(skips)
                    ?: throw Exception("No desserts exist")
            return res.asIterable().map { it }.toList()
        } catch (t: Throwable) {
            throw Exception("Cannot get desserts page")
        }
    }

    override fun getAll(): List<Dessert> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all desserts")
        }
    }

    override fun delete(id: String): Boolean {
        return try {
            col.findOneAndDelete(Dessert::id eq id)
                ?: throw Exception("No dessert with that ID exists")
            true
        } catch (t: Throwable) {
            throw Exception("Cannot delete dessert")
        }
    }

    override fun add(entry: Dessert): Dessert {
        return try {
            col.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add dessert")
        }
    }

    override fun update(entry: Dessert): Dessert {
        return try {
            col.updateOne(
                Dessert::id eq entry.id,
                Dessert::name setTo entry.name,
                Dessert::description setTo entry.description,
                Dessert::imageUrl setTo entry.imageUrl
            )
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot update dessert")
        }
    }
}


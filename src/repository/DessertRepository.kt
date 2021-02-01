package com.example.repository

import com.example.models.Dessert
import com.example.models.DessertsPage
import com.example.models.PagingInfo
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*
import kotlin.Exception

class DessertRepository(client: MongoClient) : RepositoryInterface<Dessert> {
    private val col: MongoCollection<Dessert>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Dessert>("Dessert")
    }

    fun getDessertsByUserId(userId: String): List<Dessert> {
        return try {
            col.find(Dessert::userId eq userId).asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get user desserts")
        }
    }

    fun getDessertsPage(page: Int, size: Int): DessertsPage {
        try {
            val skips = page * size
            val res = col.find().skip(skips).limit(size)
                    ?: throw Exception("No desserts exist")
            val results = res.asIterable().map { it }
            val totalDesserts = col.estimatedDocumentCount()
            val totalPages = (totalDesserts / size) + 1
            val next = if (results.isNotEmpty()) page + 1 else null
            val prev = if (page > 0) page - 1 else null
            val info = PagingInfo(totalDesserts.toInt(), totalPages.toInt(), next, prev)
            return DessertsPage(results, info)
        } catch (t: Throwable) {
            throw Exception("Cannot get desserts page")
        }
    }

    override fun getById(id: String): Dessert {
        return try {
            col.findOne(Dessert::id eq id)
                    ?: throw Exception("No dessert with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get dessert")
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
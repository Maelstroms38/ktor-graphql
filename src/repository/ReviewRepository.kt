package com.example.repository

import com.example.models.Review
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*
import java.lang.Exception

class ReviewRepository(client: MongoClient) : RepositoryInterface<Review> {
    private val col: MongoCollection<Review>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<Review>("Review")
    }

    fun getReviewsByDessertId(dessertId: String): List<Review> {
        return try {
            val res = col.find(Review::dessertId eq dessertId)
                    ?: throw Exception("No review with that dessert ID exists")
            res.asIterable().map { it }
        } catch(t: Throwable) {
            throw Exception("Cannot find reviews")
        }
    }

    override fun getById(id: String): Review {
        return try {
            col.findOne(Review::id eq id)
                    ?: throw Exception("No review with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get review")
        }
    }

    override fun getAll(): List<Review> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all reviews")
        }
    }

    override fun delete(id: String): Boolean {
        return try {
            col.findOneAndDelete(Review::id eq id)
                    ?: throw Exception("No review with that ID exists")
            true
        } catch (t: Throwable) {
            throw Exception("Cannot delete review")
        }
    }

    override fun add(entry: Review): Review {
        return try {
            col.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add review")
        }
    }

    override fun update(entry: Review): Review {
        return try {
            col.updateOne(
                    Review::id eq entry.id,
                    Review::text setTo entry.text,
                    Review::rating setTo entry.rating,
            )
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot update review")
        }
    }

}
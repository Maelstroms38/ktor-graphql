package com.example.repository

import com.example.models.User
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.litote.kmongo.*

class UserRepository(client: MongoClient) : RepositoryInterface<User> {
    private val col: MongoCollection<User>

    init {
        val database = client.getDatabase("test")
        col = database.getCollection<User>("User")
    }

    fun getUserByEmail(email: String? = null): User? {
        return try {
            col.findOne(
                    User::email eq email,
            )
        } catch (t: Throwable) {
            throw Exception("Cannot get user with that email")
        }
    }

    override fun getById(id: String): User {
        return try {
            col.findOne(User::id eq id) ?: throw Exception("no user with that ID exists")
        } catch (t: Throwable) {
            throw Exception("Cannot get user")
        }
    }

    override fun getAll(): List<User> {
        return try {
            val res = col.find()
            res.asIterable().map { it }
        } catch (t: Throwable) {
            throw Exception("Cannot get all users")
        }
    }

    override fun delete(id: String): Boolean {
        return try {
            val res = col.deleteOne<User>(User::id eq id)
            true
        } catch (t: Throwable) {
            throw Exception("Cannot delete user")
        }
    }

    override fun add(entry: User): User {
        return try {
            val res = col.insertOne(entry)
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot add user")
        }
    }

    override fun update(entry: User): User {
        return try {
            col.updateOne(
                User::id eq entry.id,
                User::email setTo entry.email,
                User::hashedPass setTo entry.hashedPass,
            )
            entry
        } catch (t: Throwable) {
            throw Exception("Cannot update user")
        }
    }
}
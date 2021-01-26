package com.example.services

import com.example.models.Profile
import com.example.repository.DessertRepository
import com.example.repository.UserRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProfileService: KoinComponent {
    private val client: MongoClient by inject()
    private val repo: UserRepository = UserRepository(client)
    private val dessertRepo: DessertRepository = DessertRepository(client)

    fun getProfile(userId: String): Profile {
        val user = repo.getById(userId)
        val desserts = dessertRepo.getDessertsByUserId(userId)
        return Profile(user, desserts)
    }
}
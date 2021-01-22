package com.example.services

import com.example.models.Review
import com.example.models.ReviewInput
import com.example.repository.ReviewRepository
import com.mongodb.client.MongoClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*

class ReviewService: KoinComponent {
    private val client: MongoClient by inject()
    private val repo = ReviewRepository(client)

    fun createReview(userId: String, dessertId: String, reviewInput: ReviewInput): Review {
        val uid = UUID.randomUUID().toString()
        val review = Review(
                id = uid,
                userId = userId,
                dessertId = dessertId,
                text = reviewInput.text,
                rating = reviewInput.rating
        )
        return repo.add(review)
    }
}
package com.example.graphql

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.example.models.*
import com.example.services.ReviewService

fun SchemaBuilder.reviewSchema(reviewService: ReviewService) {

    query("getReview") {
        description = "Get an existing review"
        resolver { reviewId: String ->
            try {
                reviewService.getReview(reviewId)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("createReview") {
        description = "Create a new review"
        resolver { dessertId: String, reviewInput: ReviewInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                reviewService.createReview(userId, dessertId, reviewInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("updateReview") {
        description = "Update an existing review"
        resolver { reviewId: String, reviewInput: ReviewInput, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                reviewService.updateReview(userId, reviewId, reviewInput)
            } catch (e: Exception) {
                null
            }
        }
    }

    mutation("deleteReview") {
        description = "Delete a review"
        resolver { reviewId: String, ctx: Context ->
            try {
                val userId = ctx.get<User>()?.id ?: error("Not signed in")
                reviewService.deleteReview(userId, reviewId)
            } catch (e: Exception) {
                null
            }
        }
    }

    inputType<ReviewInput>{
        description = "The input of the review without the identifier"
    }

    type<Review>{
        description = "Review object with the attributes text and rating"
    }
}
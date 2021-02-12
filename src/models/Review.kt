package com.example.models

data class Review(override val id: String, val userId: String, val dessertId: String, val text: String, val rating: Int): Model

data class ReviewInput(val text: String, val rating: Int)
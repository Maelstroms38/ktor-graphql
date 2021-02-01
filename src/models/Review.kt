package com.example.models

data class Review(val id: String, val userId: String, val dessertId: String, val text: String, val rating: Int)

data class ReviewInput(val text: String, val rating: Int)
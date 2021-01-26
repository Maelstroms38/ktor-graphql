package com.example.models

data class Dessert(val id: String, val userId: String, var name: String, var description: String, var imageUrl: String,
                   var reviews: List<Review> = emptyList())

data class DessertInput(val name: String, val description: String, val imageUrl: String)

data class PagingInfo(var count: Int, var pages: Int, var next: Int?, var prev: Int?)

data class DessertsPage(val results: List<Dessert>, val info: PagingInfo)

data class Review(val id: String, val userId: String, val dessertId: String, val text: String, val rating: Int)

data class ReviewInput(val text: String, val rating: Int)

data class Profile(val user: User, val desserts: List<Dessert> = emptyList())

data class User(val id: String, val email: String, val hashedPass: ByteArray)

data class UserInput(val email: String, val password: String)

data class UserResponse(val token: String, val user: User)
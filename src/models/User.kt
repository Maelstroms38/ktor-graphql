package com.example.models

data class User(val id: String, val email: String, val hashedPass: ByteArray)

data class UserInput(val email: String, val password: String)

data class UserResponse(val token: String, val user: User)
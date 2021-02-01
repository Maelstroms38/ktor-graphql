package com.example.models

data class Profile(val user: User, val desserts: List<Dessert> = emptyList())
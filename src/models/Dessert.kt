package com.example.models

data class Dessert(val id: String, var name: String, var description: String, var imageUrl: String)

data class DessertInput(val name: String, val description: String, val imageUrl: String)
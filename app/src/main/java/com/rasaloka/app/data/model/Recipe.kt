package com.rasaloka.app.data.model

data class Recipe(
    val id: String = "",
    val userId: String = "",
    val username: String = "",
    val title: String = "",
    val description: String = "",
    val ingredients: String = "",
    val steps: String = "",
    val imageBase64: String = "",
    val likesCount: Int = 0,
    val likedBy: List<String> = emptyList(),
    val commentsCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)


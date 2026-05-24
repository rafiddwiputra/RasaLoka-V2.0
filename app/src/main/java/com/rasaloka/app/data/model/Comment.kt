package com.rasaloka.app.data.model

data class Comment(

    val id: String = "",

    val recipeId: String = "",

    val userId: String = "",

    val username: String = "",

    val text: String = "",

    val createdAt: Long = System.currentTimeMillis()
)
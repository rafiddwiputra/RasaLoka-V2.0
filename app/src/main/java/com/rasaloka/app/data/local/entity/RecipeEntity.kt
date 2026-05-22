package com.rasaloka.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(

    @PrimaryKey
    val id: String,

    val userId: String,
    val username: String,

    val title: String,
    val description: String,
    val ingredients: String,
    val steps: String,

    val imageBase64: String,

    val likesCount: Int,
    val likedBy: List<String>,
    val commentsCount: Int,

    val createdAt: Long
)
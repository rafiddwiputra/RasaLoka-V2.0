package com.rasaloka.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class SavedRecipeEntity(

    @PrimaryKey
    val recipeId: String,

    val userId: String,

    val title: String,
    val description: String,

    val imageBase64: String
)
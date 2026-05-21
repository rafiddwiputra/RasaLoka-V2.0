package com.rasaloka.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rasaloka.app.data.local.dao.RecipeDao
import com.rasaloka.app.data.local.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}
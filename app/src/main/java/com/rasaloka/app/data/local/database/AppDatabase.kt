package com.rasaloka.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasaloka.app.data.local.dao.RecipeDao
import com.rasaloka.app.data.local.entity.RecipeEntity
import com.rasaloka.app.data.local.entity.SavedRecipeEntity
import com.rasaloka.app.data.local.dao.SavedRecipeDao

@Database(
    entities = [
        RecipeEntity::class,
        SavedRecipeEntity::class],
    version = 3,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun savedRecipeDao(): SavedRecipeDao

}
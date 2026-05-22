package com.rasaloka.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rasaloka.app.data.local.dao.RecipeDao
import com.rasaloka.app.data.local.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class],
    version = 2,
    exportSchema = false
)

@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

}
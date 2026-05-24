package com.rasaloka.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rasaloka.app.data.local.entity.SavedRecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedRecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRecipe(
        recipe: SavedRecipeEntity
    )

    @Query("""
        SELECT * FROM saved_recipes
        WHERE userId = :userId
    """)
    fun getSavedRecipes(
        userId: String
    ): Flow<List<SavedRecipeEntity>>

    @Query("""
        DELETE FROM saved_recipes
        WHERE recipeId = :recipeId
        AND userId = :userId
    """)
    suspend fun unsaveRecipe(
        recipeId: String,
        userId: String
    )

    @Query("""
        SELECT EXISTS(
            SELECT 1
            FROM saved_recipes
            WHERE recipeId = :recipeId
            AND userId = :userId
        )
    """)
    suspend fun isRecipeSaved(
        recipeId: String,
        userId: String
    ): Boolean
}
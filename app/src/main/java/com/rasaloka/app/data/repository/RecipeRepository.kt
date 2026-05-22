package com.rasaloka.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.rasaloka.app.data.local.dao.RecipeDao
import com.rasaloka.app.data.local.entity.RecipeEntity
import com.rasaloka.app.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.map

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val firestore: FirebaseFirestore
) {

    // =========================
    // ROOM DATABASE
    // =========================

    fun getAllRecipes(): Flow<List<RecipeEntity>> {
        return recipeDao.getAllRecipes()
    }

    fun getMyRecipes(userId: String): Flow<List<RecipeEntity>> {
        return recipeDao.getMyRecipes(userId)
    }

    fun observeRecipes(): Flow<List<Recipe>> {

        return recipeDao
            .getAllRecipes()
            .map { entities ->

                entities.map { entity ->

                    Recipe(
                        id = entity.id,
                        userId = entity.userId,
                        username = entity.username,
                        title = entity.title,
                        description = entity.description,
                        ingredients = entity.ingredients,
                        steps = entity.steps,
                        imageBase64 = entity.imageBase64,
                        likesCount = entity.likesCount,
                        commentsCount = entity.commentsCount,
                        createdAt = entity.createdAt
                    )
                }
            }
    }

    suspend fun insertRecipe(recipe: RecipeEntity) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) {
        recipeDao.deleteRecipe(recipe)
    }

    suspend fun updateRecipe(
        recipe: Recipe,
        recipeEntity: RecipeEntity
    ) {

        // update firestore
        firestore
            .collection("recipes")
            .document(recipe.id)
            .set(recipe)
            .await()

        // update room
        recipeDao.updateRecipe(recipeEntity)
    }

    // =========================
    // FIRESTORE
    // =========================

    suspend fun addRecipe(recipe: Recipe) {

        firestore
            .collection("recipes")
            .document(recipe.id)
            .set(recipe)
            .await()
    }

    suspend fun fetchRecipes(): List<Recipe> {

        return firestore
            .collection("recipes")
            .get()
            .await()
            .toObjects(Recipe::class.java)
    }

    suspend fun deleteRecipeFromFirestore(recipeId: String) {

        firestore
            .collection("recipes")
            .document(recipeId)
            .delete()
            .await()
    }

    // =========================
    // SIMPAN KE ROOM + FIRESTORE
    // =========================

    suspend fun saveRecipe(
        recipe: Recipe,
        recipeEntity: RecipeEntity
    ) {

        // simpan online
        addRecipe(recipe)

        // simpan offline
        insertRecipe(recipeEntity)
    }

// =========================
// MENGAMBIL RESEP BERDASARKAN ID
// =========================

    suspend fun getRecipeById(
        recipeId: String
    ): Recipe? {

        val document = firestore
            .collection("recipes")
            .document(recipeId)
            .get()
            .await()

        return document.toObject(Recipe::class.java)
    }
}
package com.rasaloka.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import com.rasaloka.app.data.local.dao.RecipeDao
import com.rasaloka.app.data.local.entity.RecipeEntity
import com.rasaloka.app.data.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.flow.map
import com.google.firebase.firestore.FieldValue
import com.rasaloka.app.data.local.dao.SavedRecipeDao
import com.rasaloka.app.data.local.entity.SavedRecipeEntity
import com.rasaloka.app.data.model.Comment

class RecipeRepository(
    private val recipeDao: RecipeDao,
    private val savedRecipeDao: SavedRecipeDao,
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
                        likedBy = entity.likedBy,
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

// =========================
// TOGGLE LIKE
// =========================

    suspend fun toggleLike(
        recipeId: String,
        userId: String,
        isCurrentlyLiked: Boolean
    ) {

        val recipeRef = firestore
            .collection("recipes")
            .document(recipeId)

        if (isCurrentlyLiked) {

            // UNLIKE
            recipeRef.update(
                mapOf(
                    "likesCount" to FieldValue.increment(-1),
                    "likedBy" to FieldValue.arrayRemove(userId)
                )
            ).await()

        } else {

            // LIKE
            recipeRef.update(
                mapOf(
                    "likesCount" to FieldValue.increment(1),
                    "likedBy" to FieldValue.arrayUnion(userId)
                )
            ).await()
        }
    }

// =========================
// TAMBAH COMMENT
// =========================

    suspend fun addComment(
        comment: Comment
    ) {

        // simpan comment ke subcollection comments
        firestore
            .collection("recipes")
            .document(comment.recipeId)
            .collection("comments")
            .document(comment.id)
            .set(comment)
            .await()

        // ambil total comment terbaru
        val commentsSnapshot = firestore
            .collection("recipes")
            .document(comment.recipeId)
            .collection("comments")
            .get()
            .await()

        // update commentsCount di recipe
        firestore
            .collection("recipes")
            .document(comment.recipeId)
            .update(
                "commentsCount",
                commentsSnapshot.size()
            )
            .await()
    }

// =========================
// AMBIL COMMENTS
// =========================

    suspend fun getComments(
        recipeId: String
    ): List<Comment> {

        return firestore
            .collection("recipes")
            .document(recipeId)
            .collection("comments")
            .get()
            .await()
            .toObjects(Comment::class.java)
            .sortedByDescending {
                it.createdAt
            }
    }

    // =========================
// SAVE RECIPE
// =========================

    suspend fun saveRecipe(
        savedRecipe: SavedRecipeEntity
    ) {

        savedRecipeDao.saveRecipe(savedRecipe)
    }

    fun getSavedRecipes(
        userId: String
    ): Flow<List<SavedRecipeEntity>> {

        return savedRecipeDao
            .getSavedRecipes(userId)
    }

    suspend fun unsaveRecipe(
        recipeId: String,
        userId: String
    ) {

        savedRecipeDao.unsaveRecipe(
            recipeId,
            userId
        )
    }

    suspend fun isRecipeSaved(
        recipeId: String,
        userId: String
    ): Boolean {

        return savedRecipeDao
            .isRecipeSaved(
                recipeId,
                userId
            )
    }
}
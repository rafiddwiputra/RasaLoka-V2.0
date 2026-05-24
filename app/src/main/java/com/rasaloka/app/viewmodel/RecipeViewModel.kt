package com.rasaloka.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasaloka.app.data.local.entity.RecipeEntity
import com.rasaloka.app.data.model.Recipe
import com.rasaloka.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import com.rasaloka.app.data.model.Comment

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    // =========================
    // STATE RESEP ONLINE
    // =========================

    private val _onlineRecipes =
        MutableStateFlow<List<Recipe>>(emptyList())

    val onlineRecipes: StateFlow<List<Recipe>> =
        _onlineRecipes.asStateFlow()

    // =========================
    // STATE RESEP OFFLINE
    // =========================

    private val _myRecipes =
        MutableStateFlow<List<Recipe>>(emptyList())

    val myRecipes: StateFlow<List<Recipe>> =
        _myRecipes.asStateFlow()

    fun observeMyRecipes() {

        viewModelScope.launch {

            repository
                .observeRecipes()
                .collect { recipes ->

                    _myRecipes.value = recipes
                }
        }
    }

    private val _selectedRecipe =
        MutableStateFlow<Recipe?>(null)

    val selectedRecipe: StateFlow<Recipe?> =
        _selectedRecipe.asStateFlow()

    fun fetchOnlineRecipes() {

        viewModelScope.launch {

            try {

                val recipes =
                    repository.fetchRecipes()

                _onlineRecipes.value = recipes

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun getRecipeById(
        recipeId: String
    ) {

        viewModelScope.launch {

            try {

                val recipe =
                    repository.getRecipeById(recipeId)

                _selectedRecipe.value = recipe

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

// =========================
// TAMBAH RESEP
// =========================

    fun addRecipe(
        recipe: Recipe,
        recipeEntity: RecipeEntity
    ) {

        viewModelScope.launch {

            try {

                repository.saveRecipe(
                    recipe,
                    recipeEntity
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    // =========================
// UPDATE RESEP
// =========================

    fun updateRecipe(
        recipe: Recipe,
        recipeEntity: RecipeEntity
    ) {

        viewModelScope.launch {

            try {

                repository.updateRecipe(
                    recipe,
                    recipeEntity
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    // =========================
    // HAPUS RESEP
    // =========================

    fun deleteRecipe(
        recipeId: String,
        recipeEntity: RecipeEntity
    ) {

        viewModelScope.launch {

            try {

                repository.deleteRecipeFromFirestore(recipeId)

                repository.deleteRecipe(recipeEntity)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

// =========================
// TOGGLE LIKE
// =========================

    fun toggleLike(
        recipeId: String,
        userId: String,
        isCurrentlyLiked: Boolean
    ) {

        viewModelScope.launch {

            try {

                repository.toggleLike(
                    recipeId = recipeId,
                    userId = userId,
                    isCurrentlyLiked = isCurrentlyLiked
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

// =========================
// STATE COMMENT
// =========================

    private val _comments =
        MutableStateFlow<List<Comment>>(emptyList())

    val comments: StateFlow<List<Comment>> =
        _comments.asStateFlow()

    fun loadComments(recipeId: String) {

        viewModelScope.launch {

            try {

                val result =
                    repository.getComments(recipeId)

                _comments.value = result

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

// =========================
// TAMBAH COMMENT
// =========================

    fun addComment(
        comment: Comment
    ) {

        viewModelScope.launch {

            try {

                repository.addComment(comment)

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}
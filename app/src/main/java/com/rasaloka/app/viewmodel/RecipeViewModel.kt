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

class RecipeViewModel(
    private val repository: RecipeRepository
) : ViewModel() {

    // =========================
    // STATE RESEP ONLINE
    // =========================

    private val _recipes =
        MutableStateFlow<List<Recipe>>(emptyList())

    val recipes: StateFlow<List<Recipe>> =
        _recipes.asStateFlow()

    private val _selectedRecipe =
        MutableStateFlow<Recipe?>(null)

    val selectedRecipe: StateFlow<Recipe?> =
        _selectedRecipe.asStateFlow()

// =========================
// OBSERVE ROOM DATABASE
// =========================

    fun observeRecipes() {

        viewModelScope.launch {

            repository
                .observeRecipes()
                .collect { recipes ->

                    _recipes.value = recipes
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
}
package com.rasaloka.app.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rasaloka.app.data.repository.RecipeRepository
import com.rasaloka.app.viewmodel.RecipeViewModel

class RecipeViewModelFactory(
    private val repository: RecipeRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(
                RecipeViewModel::class.java
            )
        ) {

            return RecipeViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}
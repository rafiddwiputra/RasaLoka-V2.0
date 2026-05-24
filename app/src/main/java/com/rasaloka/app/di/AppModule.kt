package com.rasaloka.app.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.rasaloka.app.data.local.database.AppDatabase
import com.rasaloka.app.data.repository.RecipeRepository
import com.rasaloka.app.viewmodel.factory.RecipeViewModelFactory

object AppModule {

    // =========================
    // ROOM DATABASE
    // =========================

    private var database: AppDatabase? = null

    fun provideDatabase(
        context: Context
    ): AppDatabase {

        return database ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rasaloka_database"
            ).build()

            database = instance

            instance
        }
    }

    // =========================
    // DAO
    // =========================

    fun provideRecipeDao(
        context: Context
    ) = provideDatabase(context)
        .recipeDao()

    fun provideSavedRecipeDao(
        context: Context
    ) = provideDatabase(context)
        .savedRecipeDao()

    // =========================
    // FIRESTORE
    // =========================

    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // =========================
    // REPOSITORY
    // =========================

    fun provideRecipeRepository(
        context: Context
    ): RecipeRepository {

        return RecipeRepository(
            recipeDao = provideRecipeDao(context),
            savedRecipeDao = provideSavedRecipeDao(context),
            firestore = provideFirestore()
        )
    }

    // =========================
    // VIEWMODEL FACTORY
    // =========================

    fun provideRecipeViewModelFactory(
        context: Context
    ): RecipeViewModelFactory {

        return RecipeViewModelFactory(
            provideRecipeRepository(context)
        )
    }
}
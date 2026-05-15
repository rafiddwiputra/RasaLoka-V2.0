package com.rasaloka.app.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseService {

    // Instance yang digunakan untuk Authentication (LOGIN n LOGOUT)
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    // Instance yang digunakan untuk Firestore Database untuk (SIMPAN DATA RESEP)
    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    // Fungsi yang digunakan untuk mengambil referensi collection "recipes"
    fun getRecipeCollection() = firestore.collection("recipes")
}
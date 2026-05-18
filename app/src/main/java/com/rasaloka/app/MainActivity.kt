package com.rasaloka.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rasaloka.app.ui.screen.CommentScreen
import com.rasaloka.app.ui.screen.HomeScreen
import com.rasaloka.app.ui.screen.LoginScreen
import com.rasaloka.app.ui.theme.RasaLokaV20Theme
import com.rasaloka.app.ui.screen.SavedScreen
import com.rasaloka.app.ui.screen.MyRecipeScreen
import com.rasaloka.app.ui.screen.ProfilScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RasaLokaV20Theme {
                // NavigasiUtama adalah tempat kita mengatur perpindahan layar
                NavigasiUtama()
            }
        }
    }
}

@Composable
fun NavigasiUtama() {
    // Pengendali navigasi
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        // NavHost menentukan layar mana yang muncul berdasarkan "route"
        NavHost(
            navController = navController,
            startDestination = "login", // Layar pertama yang muncul
            modifier = Modifier.padding(innerPadding)
        ) {
            // Rute untuk Layar Login
            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        // Ketika login sukses, pindah ke rute "home"
                        navController.navigate("home") {
                            // Menghapus layar login dari tumpukan agar user tidak bisa kembali ke login dengan tombol back
                            popUpTo("login") { inclusive = true }
                        }
                    }
                )
            }

            // Rute untuk Halaman Utama
            composable("home") {
                HomeScreen(navController)
            }

            composable("saved") {
                SavedScreen(navController)
            }

            composable("myrecipe") {
                MyRecipeScreen(navController)
            }

            composable("profil") {
                ProfilScreen(navController)
            }

            composable("comment") {
                CommentScreen(navController)
            }
        }
    }
}
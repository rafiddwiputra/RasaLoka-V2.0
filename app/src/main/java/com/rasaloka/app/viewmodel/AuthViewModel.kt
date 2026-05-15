package com.rasaloka.app.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.GoogleAuthProvider
import com.rasaloka.app.data.remote.FirebaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Kode ini adalah state atau sebuah kondisi layar login
sealed class LoginState{
    object Idle: LoginState() // Diam atau belum melakukan apa-apa
    object Loading: LoginState() // Sedang proses loading
    object Success: LoginState() // Login Berhasil
    data class Error (val message: String) : LoginState() // Jika login gagal
}

class AuthViewModel : ViewModel() {

    // Memanggil Firebase dari gudang utama yang sudah dibuat sebelumnya
    private val auth = FirebaseService.auth

    // Variabel yang digunakan untuk menyimpan kondisi login saat ini (agar UI bisa bereaksi)
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    // Fungsi ini akan dipanggil ketika setelah user sudah memilih akun Google di layar HP
    fun signInWithGoogle(idToken: String) {
        _loginState.value = LoginState.Loading // Ubah tampilan menjadi loading (Muter-Muter)

        // Membuat "Kunci Masuk" dari akun Google user
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        // Memasukkan kunci tersebut ke Firebase
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success //Berhasil
                } else {
                    val errorMessage = task.exception?.message ?: "Login Gagal"
                    _loginState.value = LoginState.Error(errorMessage) // Jika Gagal
                }
            }
    }
        // Fungsi yang digunakan untuk cek apakah user sudah login sebelumnya (Fungsinya agar user tidak login berulang)
        fun checkUserLoggedIn() : Boolean {
            return auth.currentUser!=null
        }
    }

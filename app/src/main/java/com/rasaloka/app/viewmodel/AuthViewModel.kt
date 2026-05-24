package com.rasaloka.app.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.GoogleAuthProvider
import com.rasaloka.app.data.remote.FirebaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.UserProfileChangeRequest

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

    // Ambil UID user login
    fun getCurrentUserId(): String {
        return auth.currentUser?.uid ?: ""
    }

    // Ambil username user login
    fun getCurrentUsername(): String {
        return auth.currentUser?.displayName ?: "User"
    }

    // Ambil email user login
    fun getCurrentUserEmail(): String {
        return auth.currentUser?.email ?: ""
    }

    // Update nama user
    fun updateUsername(
        newUsername: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {

        val user = auth.currentUser

        if (user == null) {
            onError("User tidak ditemukan")
            return
        }

        val profileUpdates =
            UserProfileChangeRequest.Builder()
                .setDisplayName(newUsername)
                .build()

        user.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->

                if (task.isSuccessful) {

                    onSuccess()

                } else {

                    onError(
                        task.exception?.message
                            ?: "Gagal update nama"
                    )
                }
            }
    }

    // Logout user
    fun logout() {

        auth.signOut()
    }

}
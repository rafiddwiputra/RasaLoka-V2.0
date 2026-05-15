package com.rasaloka.app.ui.screen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.rasaloka.app.R
import com.rasaloka.app.viewmodel.AuthViewModel
import com.rasaloka.app.viewmodel.LoginState

// 1. FUNGSI UTAMA LOGIKA LOGIN SCREEN
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val context = LocalContext.current
    val loginState by authViewModel.loginState.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { token ->
                    authViewModel.signInWithGoogle(token)
                }
            } catch (e: ApiException) {

            }
        }
    }

    LaunchedEffect(Unit) {
        if (authViewModel.checkUserLoggedIn()) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(loginState) {
        if (loginState is LoginState.Success) {
            onLoginSuccess()
        }
    }

    // Menggunakan Surface untuk mengatur warna Background utama layar
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F3) // Warna Background krem sesuai desain Figma
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HeaderSection()
            Spacer(modifier = Modifier.height(40.dp))
            FormInputSection()
            Spacer(modifier = Modifier.height(32.dp))
            ActionButtonsSection(
                onGoogleLoginClick = {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(context, gso)
                    launcher.launch(googleSignInClient.signInIntent)
                }
            )
            if (loginState is LoginState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            if (loginState is LoginState.Loading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(color = Color(0xFFFF5722))
            }
        }
    }
}

// BAGIAN HEADER (LOGO & JUDUL)
@Composable
fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_rasaloka),
            contentDescription = "Logo Rasa Loka",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Selamat Datang di RasaLoka",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B1B1B)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Temukan dan simpan resep favoritmu",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

// BAGIAN FORM INPUT
@Composable
fun FormInputSection() {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Email",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1B1B1B)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = emailText,
            onValueChange = { emailText = it },
            placeholder = { Text("contoh@gmail.com", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color(0xFFFF5722)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Kata Sandi",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1B1B1B)
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            placeholder = { Text("********", color = Color.LightGray) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color(0xFFFF5722)
            )
        )
    }
}

// BAGIAN TOMBOL
@Composable
fun ActionButtonsSection(
    onGoogleLoginClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            onClick = { /* Nanti diisi logika login Email/Password */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF5722)
            )
        ) {
            Text(
                text = "Masuk",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color.LightGray
            )
            Text(
                text = "atau lanjutkan dengan",
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = Color.LightGray
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Kode fungsi untuk tombol LOGIN
        OutlinedButton(
            onClick = onGoogleLoginClick, // Memanggil perintah login google saat diklik
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color(0xFF1B1B1B)
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google),
                modifier = Modifier.size(24.dp),
                contentDescription = "Ikon Google"
            )

            // Memberikan jarak horizontal (ke samping) antara ikon dan teks
            Spacer(modifier= Modifier.width(12.dp))

            Text(
                text = "Masuk dengan Google",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
    }
}
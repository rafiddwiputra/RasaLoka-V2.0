package com.rasaloka.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasaloka.app.viewmodel.AuthViewModel
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast

@Composable
fun ProfilScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val context = LocalContext.current

    val isConnected =
        isInternetAvailable(context)

    var username by remember {
        mutableStateOf(
            authViewModel.getCurrentUsername()
        )
    }

    val email =
        authViewModel.getCurrentUserEmail()

    Scaffold(

        // FOOTER
        bottomBar = {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(65.dp)
                    .background(Color.White),

                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                // HOME
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("home")
                        }
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Gray
                    )

                    Text(
                        "Beranda",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                // SAVE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("saved")
                        }
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Icon(
                        Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Gray
                    )

                    Text(
                        "Resep Tersimpan",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                // RESEP SAYA
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            navController.navigate("myrecipe")
                        }
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Icon(
                        Icons.Outlined.RestaurantMenu,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Gray
                    )

                    Text(
                        "Resep Saya",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                // PROFILE ACTIVE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { }
                ) {

                    Box(
                        modifier = Modifier
                            .size(width = 45.dp, height = 4.dp)
                            .background(
                                Color(0xFFFF5722),
                                RoundedCornerShape(10.dp)
                            )
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFFFF5722)
                    )

                    Text(
                        "Profil",
                        fontSize = 10.sp,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFFFF9F3))
        ) {

            // HEADER
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFF5722)),

                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = "Profil",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (!isConnected) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            Color(0xFFFFE0B2),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "⚠ Tidak ada koneksi internet\nNama tidak dapat diperbarui",
                        color = Color(0xFFE65100),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))
            }

            // CONTENT PROFILE
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // ICON PROFILE
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            Color.White,
                            RoundedCornerShape(50.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Color(0xFFFF5722),
                        modifier = Modifier.size(55.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                // LABEL NAMA
                Text(
                    text = "Nama",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // INPUT NAMA (Ubah warna border unfocused ke oren)
                OutlinedTextField(
                    value = username,

                    onValueChange = {
                        username = it
                    },

                    enabled = isConnected,

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color(0xFFFF5722)
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // LABEL EMAIL
                Text(
                    text = "Email",
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // INPUT EMAIL (Ubah warna border unfocused ke oren)
                OutlinedTextField(
                    value = email,
                    onValueChange = {},
                    readOnly = true,

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color(0xFFFF5722)
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                // BUTTON KELUAR (Disesuaikan isi tampilannya pakai Row + Ikon + Teks bertumpuk)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Red,
                            RoundedCornerShape(14.dp)
                        )
                        .background(
                            Color.White,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable {

                            authViewModel.logout()

                            navController.navigate("login") {

                                popUpTo(0)
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                        contentDescription = "Keluar",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = "Keluar",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Keluar dari akun Anda",
                            color = Color.Red.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BUTTON SIMPAN
            Button(
                onClick = {

                    if (!isConnected) {

                        Toast.makeText(
                            context,
                            "Tidak ada koneksi internet",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@Button
                    }

                    authViewModel.updateUsername(

                        newUsername = username,

                        onSuccess = {

                            Toast.makeText(
                                context,
                                "Nama berhasil diperbarui",
                                Toast.LENGTH_SHORT
                            ).show()
                        },

                        onError = {

                            Toast.makeText(
                                context,
                                it,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },

                enabled = isConnected,

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                ),

                shape = RoundedCornerShape(16.dp),

                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 50.dp)
                    .height(50.dp)
            ) {

                Text(
                    text = "Simpan Perubahan",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
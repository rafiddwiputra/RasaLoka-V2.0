package com.rasaloka.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun EditRecipeScreen(navController: NavController) {
    Scaffold(
        // BOTTOM BAR (Resep Saya ACTIVE)
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
                        .clickable { navController.navigate("home") }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(Icons.Outlined.Home, contentDescription = null, modifier = Modifier.size(22.dp), tint = Color.Gray)
                    Text("Beranda", fontSize = 10.sp, color = Color.Gray)
                }

                // SAVE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate("saved") }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(Icons.Outlined.BookmarkBorder, contentDescription = null, modifier = Modifier.size(22.dp), tint = Color.Gray)
                    Text("Resep Tersimpan", fontSize = 10.sp, color = Color.Gray)
                }

                // RESEP SAYA ACTIVE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate("myrecipe") }
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 45.dp, height = 4.dp)
                            .background(Color(0xFFFF5722), RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Icon(Icons.Outlined.RestaurantMenu, contentDescription = null, modifier = Modifier.size(22.dp), tint = Color(0xFFFF5722))
                    Text("Resep Saya", fontSize = 10.sp, color = Color(0xFFFF5722), fontWeight = FontWeight.Bold)
                }

                // PROFILE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate("profil") }
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Icon(Icons.Outlined.Person, contentDescription = null, modifier = Modifier.size(22.dp), tint = Color.Gray)
                    Text("Profil", fontSize = 10.sp, color = Color.Gray)
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
            // HEADER ("Edit Resep" - Rata Kiri)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFF5722))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Edit Resep",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            // KONTEN FORM SCROLLABLE
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // 1. INPUT NAMA MAKANAN (Teks Hitam)
                Column {
                    Text("Nama Makanan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(initialValue = "Nasi Goreng Spesial", placeholder = "")
                }

                // 2. INPUT DESKRIPSI (Teks Hitam)
                Column {
                    Text("Deskripsi", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(initialValue = "Nasi Goreng Spesial", placeholder = "")
                }

                // 3. INPUT BAHAN (Multi-line + Sudah Terisi Teks Hitam)
                Column {
                    Text("Bahan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        initialValue = "Masukkan bahan-bahan\nContoh:\n3 Butir Telur\n200g daging ayam",
                        placeholder = "",
                        isMultiLine = true
                    )
                }

                // 4. INPUT LANGKAH MEMASAK (Multi-line + Sudah Terisi Teks Hitam)
                Column {
                    Text("Langkah Memasak", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        initialValue = "Masukkan bahan-bahan\nContoh:\n3 Butir Telur\n200g daging ayam",
                        placeholder = "",
                        isMultiLine = true
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // TOMBOL AKSI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Batal
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .border(1.dp, Color(0xFFFF5722), RoundedCornerShape(12.dp))
                    ) {
                        Text("Batal", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }

                    // Simpan Resep
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text("Simpan Resep", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

// Komponen Input Khusus Halaman Edit (Tinggi Ideal 56.dp, Anti-Potong Teks)
@Composable
fun EditInputField(initialValue: String, placeholder: String, isMultiLine: Boolean = false) {
    var textValue by remember { mutableStateOf(initialValue) }

    OutlinedTextField(
        value = textValue,
        onValueChange = { textValue = it },
        placeholder = {
            if (placeholder.isNotEmpty()) {
                Text(text = placeholder, color = Color.LightGray, fontSize = 13.sp, lineHeight = 18.sp)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFD1C4),
            unfocusedBorderColor = Color(0xFFFFD1C4),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = !isMultiLine,
        minLines = if (isMultiLine) 4 else 1,
        maxLines = if (isMultiLine) 6 else 1,
        modifier = Modifier
            .fillMaxWidth()
            .then(if (!isMultiLine) Modifier.height(56.dp) else Modifier)
    )
}
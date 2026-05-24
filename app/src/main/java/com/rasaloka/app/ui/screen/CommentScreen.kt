package com.rasaloka.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import java.util.UUID
import com.google.firebase.auth.FirebaseAuth
import com.rasaloka.app.viewmodel.RecipeViewModel
import com.rasaloka.app.data.model.Comment
import com.rasaloka.app.di.AppModule
import androidx.compose.foundation.layout.imePadding

// 1. Struktur Data Komentar

// 2. Data Dummy (Isi Komentar)

// 3. Desain Komponen Kotak Komentar (Masing-masing pakai Card Putih)
@Composable
fun CommentItem(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp) // Jarak antar kotak card
            .border(1.dp, Color(0xFFFF5722), RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = comment.username,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = comment.text,
                fontSize = 14.sp,
                color = Color(0xFF333333)
            )
        }
    }
}

// Untuk Komentar
@Composable
fun CommentScreen(
    navController: NavController,
    recipeId: String
) {
    val context = navController.context

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule.provideRecipeViewModelFactory(context)
    )

    LaunchedEffect(recipeId) {
        viewModel.loadComments(recipeId)
    }

    val comments = viewModel.comments.collectAsState().value

    var commentText by remember {
        mutableStateOf("")
    }

    Scaffold(
        // BOTTOM BAR (Input Komentar Oranye Pendek + Bottom Navigation Beranda Aktif)
        bottomBar = {
            Column(
                modifier = Modifier
                    .background(Color(0xFFFFF9F3))
            ) {

                Column(
                    modifier = Modifier.imePadding()
                ) {
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = {
                            commentText = it
                        },
                        placeholder = {
                            Text(text = "Beri Komentar...", color = Color.Gray, fontSize = 14.sp)
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color(0xFFFF5722),
                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable {

                                        if (commentText.isNotBlank()) {

                                            val user = FirebaseAuth
                                                .getInstance()
                                                .currentUser

                                            val comment = Comment(
                                                id = UUID.randomUUID().toString(),
                                                recipeId = recipeId,
                                                userId = user?.uid ?: "",
                                                username = user?.displayName ?: "User",
                                                text = commentText
                                            )

                                            viewModel.addComment(comment)

                                            commentText = ""

                                            viewModel.loadComments(recipeId)
                                        }
                                    }
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFFF5722),
                            unfocusedBorderColor = Color(0xFFFF5722),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(30.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .height(54.dp)
                    )

                    // Jarak pembatas agar kolom komentar naik (tidak mepet dengan Bottom Nav)
                    Spacer(modifier = Modifier.height(16.dp))

                }
                    // ====================================================================

                    // BOTTOM NAVIGATION (Beranda ACTIVE)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(65.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                    // HOME ACTIVE
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("home") }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 45.dp, height = 4.dp)
                                .background(Color(0xFFFF5722), RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color(0xFFFF5722)
                        )
                        Text(
                            "Beranda",
                            fontSize = 10.sp,
                            color = Color(0xFFFF5722),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // SAVE
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("saved") }
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Icon(
                            Icons.Outlined.BookmarkBorder,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color.Gray
                        )
                        Text("Resep Tersimpan", fontSize = 10.sp, color = Color.Gray)
                    }

                    // RESEP SAYA
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("myrecipe") }
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Icon(
                            Icons.Outlined.RestaurantMenu,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color.Gray
                        )
                        Text("Resep Saya", fontSize = 10.sp, color = Color.Gray)
                    }

                    // PROFILE
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { navController.navigate("profil") }
                    ) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Icon(
                            Icons.Outlined.Person,
                            contentDescription = null,
                            modifier = Modifier.size(22.dp),
                            tint = Color.Gray
                        )
                        Text("Profil", fontSize = 10.sp, color = Color.Gray)
                    }
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
            // HEADER (Rata Kiri, Tinggi 70.dp, Padding 20.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFF5722))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Komentar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // DAFTAR KOMENTAR DENGAN CARD
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                contentPadding = PaddingValues(
                    bottom = 16.dp
                )
            ) {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }
        }
    }
}
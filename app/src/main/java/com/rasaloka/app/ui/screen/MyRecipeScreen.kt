package com.rasaloka.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.rasaloka.app.viewmodel.RecipeViewModel
import com.rasaloka.app.di.AppModule
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import android.graphics.BitmapFactory
import com.rasaloka.app.data.local.entity.RecipeEntity


@Composable
fun MyRecipeScreen(navController: NavController) {

    val context = LocalContext.current

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule.provideRecipeViewModelFactory(context)
    )

    LaunchedEffect(Unit) {
        viewModel.observeMyRecipes()
    }

    val recipes by viewModel
        .myRecipes
        .collectAsState()

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
                        .clickable { }
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
            // HEADER (Sudah Diperbaiki)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFF5722))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Resep Saya",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    // Tombol Tambah: Fungsi pindah halaman dipasang pas di sini 👇
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            navController.navigate("add_recipe")
                        }
                    ) {
                        Text(
                            text = "Tambah",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Tambah",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // GRID RESEP
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    start = 18.dp,
                    end = 18.dp,
                    top = 4.dp,
                    bottom = 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(recipes) { recipe ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("detail_recipe/${recipe.id}")
                            }
                    ) {
                        Column {

                            if (recipe.imageBase64.isNotEmpty()) {

                                val imageBytes = Base64.decode(
                                    recipe.imageBase64,
                                    Base64.DEFAULT
                                )

                                val bitmap = BitmapFactory.decodeByteArray(
                                    imageBytes,
                                    0,
                                    imageBytes.size
                                )

                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = recipe.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 16.dp,
                                                topEnd = 16.dp
                                            )
                                        )
                                )
                            }

                            Column(
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Text(
                                    text = recipe.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = recipe.description,
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // BUTTON EDIT
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .weight(1f)
                                            .border(1.dp, Color(0xFFFF5722), RoundedCornerShape(20.dp))
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color(0xFFFFF9F3))
                                            .clickable {
                                                navController.navigate(
                                                    "edit_recipe/${recipe.id}"
                                                )
                                            }
                                            .padding(vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "Edit",
                                            color = Color(0xFFFF5722),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }

                                    // BUTTON HAPUS
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .weight(1f)
                                            .border(1.dp, Color(0xFFFF5722), RoundedCornerShape(20.dp))
                                            .clip(RoundedCornerShape(20.dp))
                                            .background(Color(0xFFFFF9F3))
                                            .clickable {

                                                val recipeEntity = RecipeEntity(
                                                    id = recipe.id,
                                                    userId = recipe.userId,
                                                    username = recipe.username,
                                                    title = recipe.title,
                                                    description = recipe.description,
                                                    ingredients = recipe.ingredients,
                                                    steps = recipe.steps,
                                                    imageBase64 = recipe.imageBase64,
                                                    likesCount = recipe.likesCount,
                                                    commentsCount = recipe.commentsCount,
                                                    createdAt = recipe.createdAt
                                                )

                                                viewModel.deleteRecipe(
                                                    recipe.id,
                                                    recipeEntity
                                                )
                                            }
                                            .padding(vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = "Hapus",
                                            color = Color(0xFFFF5722),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
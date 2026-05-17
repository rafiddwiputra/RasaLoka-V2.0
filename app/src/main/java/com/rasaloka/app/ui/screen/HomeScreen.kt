package com.rasaloka.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.RestaurantMenu
import androidx.navigation.NavController
import com.rasaloka.app.R

data class Recipe(
    val title: String,
    val image: Int
)

@Composable
fun HomeScreen(navController: NavController) {

    val recipes = listOf(
        Recipe("Margherita Pizza", R.drawable.pizza),
        Recipe("Grilled Salmon", R.drawable.salmon),
        Recipe("Margherita Pizza", R.drawable.pizza),
        Recipe("Grilled Salmon", R.drawable.salmon),
        Recipe("Margherita Pizza", R.drawable.pizza),
        Recipe("Grilled Salmon", R.drawable.salmon),
    )

    Scaffold(
        bottomBar = {

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

                // resep saya
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { navController.navigate("myrecipe")}
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

                // PROFILE
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {navController.navigate("profil") }
                ) {

                    Spacer(modifier = Modifier.height(10.dp))

                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color.Gray
                    )

                    Text(
                        "Profil",
                        fontSize = 10.sp,
                        color = Color.Gray
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
                    .height(120.dp)
                    .background(Color(0xFFFF5722))
                    .padding(20.dp)
            ) {

                Column {
                    Text(
                        text = "Selamat Datang",
                        color = Color.White,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Apa yang ingin kamu masak hari ini?",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }

            // SEARCH BAR
            OutlinedTextField(
                value = "",
                onValueChange = {},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5722),
                    unfocusedBorderColor = Color(0xFFFF5722)
                ),
                leadingIcon = {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                },
                placeholder = {
                    Text("Cari Resep...")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp)
            )

            // TITLE
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Semua Resep",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Lihat Semua",
                    color = Color(0xFFFF5722)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

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

            ) {

                items(recipes) { recipe ->

                    var isLiked by remember {
                        mutableStateOf(false)
                    }

                    var isSaved by remember {
                        mutableStateOf(false)
                    }

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column {

                            Image(
                                painter = painterResource(id = recipe.image),
                                contentDescription = null,
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
                                    text = "Makanan lezat dan mudah dibuat",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        // LIKE
                                        Icon(
                                            imageVector = if (isLiked)
                                                Icons.Filled.Favorite
                                            else
                                                Icons.Outlined.FavoriteBorder,

                                            contentDescription = "Like",

                                            tint = if (isLiked)
                                                Color.Red
                                            else
                                                Color.Gray,

                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable {
                                                    isLiked = !isLiked
                                                }
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        // COMMENT
                                        Icon(
                                            imageVector = Icons.Outlined.ChatBubbleOutline,
                                            contentDescription = "Comment",
                                            tint = Color.Gray,
                                            modifier = Modifier
                                                .size(20.dp)
                                                .clickable { }
                                        )
                                    }

                                    // SAVE
                                    Icon(
                                        imageVector = if (isSaved)
                                            Icons.Filled.Bookmark
                                        else
                                            Icons.Outlined.BookmarkBorder,

                                        contentDescription = "Save",

                                        tint = if (isSaved)
                                            Color(0xFFFF5722)
                                        else
                                            Color.Gray,

                                        modifier = Modifier
                                            .size(20.dp)
                                            .clickable {
                                                isSaved = !isSaved
                                            }
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
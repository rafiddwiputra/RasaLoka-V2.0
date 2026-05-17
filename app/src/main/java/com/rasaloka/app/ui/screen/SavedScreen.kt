package com.rasaloka.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.rasaloka.app.R

data class SavedRecipe(
    val title: String,
    val image: Int
)

@Composable
fun SavedScreen(navController: NavController) {

    val savedRecipes = listOf(
        SavedRecipe("Margherita Pizza", R.drawable.pizza),
        SavedRecipe("Grilled Salmon", R.drawable.salmon),
        SavedRecipe("Margherita Pizza", R.drawable.pizza),
        SavedRecipe("Grilled Salmon", R.drawable.salmon),
        SavedRecipe("Margherita Pizza", R.drawable.pizza),
        SavedRecipe("Grilled Salmon", R.drawable.salmon),
        SavedRecipe("Margherita Pizza", R.drawable.pizza),
        SavedRecipe("Grilled Salmon", R.drawable.salmon),
    )

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

                // SAVE ACTIVE
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
                        Icons.Outlined.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = Color(0xFFFF5722)
                    )

                    Text(
                        "Resep Tersimpan",
                        fontSize = 10.sp,
                        color = Color(0xFFFF5722),
                        fontWeight = FontWeight.Bold
                    )
                }

                // RESEP SAYA
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { }
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
                        .clickable { }
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
                    .height(70.dp)
                    .background(Color(0xFFFF5722)),
                contentAlignment = Alignment.CenterStart
            ) {

                Text(
                    text = "Resep Tersimpan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
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

            ) {

                items(savedRecipes) { recipe ->

                    Card(
                        shape = RoundedCornerShape(16.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),

                        elevation = CardDefaults.cardElevation(4.dp),

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

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {

                                    var isSaved by remember {
                                        mutableStateOf(true)
                                    }

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
                                            .size(18.dp)
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
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasaloka.app.di.AppModule
import com.rasaloka.app.viewmodel.RecipeViewModel
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.ui.graphics.asImageBitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.rasaloka.app.R


@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule
            .provideRecipeViewModelFactory(context)
    )

    val recipes by viewModel
        .onlineRecipes
        .collectAsState()

    var searchQuery by remember {
        mutableStateOf("")
    }

    val isConnected =
        isInternetAvailable(context)

    val filteredRecipes = recipes.filter {

        it.title.contains(
            searchQuery,
            ignoreCase = true
        ) ||

                it.description.contains(
                    searchQuery,
                    ignoreCase = true
                )
    }

    LaunchedEffect(Unit) {

        if (isConnected) {

            viewModel.fetchOnlineRecipes()
        }
    }

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
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
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

            }

            Spacer(modifier = Modifier.height(12.dp))

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
                        text = "⚠ Tidak ada koneksi internet\nSilakan periksa jaringan Anda",
                        color = Color(0xFFE65100),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

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

                items(filteredRecipes) { recipe ->

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
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                                navController.navigate(
                                    "detail_recipe/${recipe.id}"
                                )
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

                            } else {

                                Image(
                                    painter = painterResource(id = R.drawable.pizza),
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
                                    color = Color.Gray,
                                    maxLines = 2
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

                                        Spacer(modifier = Modifier.width(4.dp))

                                        // ANGKA LIKE
                                        Text(
                                            text = "25",
                                            fontSize = 12.sp,
                                            color = Color.Black
                                        )

                                        Spacer(modifier = Modifier.width(10.dp))

                                        // COMMENT
                                        Icon(
                                            imageVector = Icons.Outlined.ChatBubbleOutline,
                                            contentDescription = "Comment",
                                            tint = Color.Gray,
                                            modifier = Modifier
                                                .size(18.dp)
                                                .clickable {
                                                    navController.navigate("comment")
                                                }
                                        )

                                        Spacer(modifier = Modifier.width(4.dp))

                                        // ANGKA COMMENT
                                        Text(
                                            text = "25",
                                            fontSize = 12.sp,
                                            color = Color.Black
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
fun isInternetAvailable(
    context: android.content.Context
): Boolean {

    val connectivityManager =
        context.getSystemService(
            android.content.Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    val network =
        connectivityManager.activeNetwork
            ?: return false

    val capabilities =
        connectivityManager.getNetworkCapabilities(network)
            ?: return false

    return capabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
    )
}
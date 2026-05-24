package com.rasaloka.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rasaloka.app.di.AppModule
import com.rasaloka.app.viewmodel.RecipeViewModel
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.graphics.asImageBitmap
import com.rasaloka.app.R
import com.google.firebase.auth.FirebaseAuth
import com.rasaloka.app.data.local.entity.SavedRecipeEntity

@Composable
fun DetailRecipeScreen(
    navController: NavController,
    recipeId: String
) {

    val context = LocalContext.current

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule
            .provideRecipeViewModelFactory(context)
    )

    val recipe by viewModel
        .selectedRecipe
        .collectAsState()

    LaunchedEffect(Unit) {

        viewModel.getRecipeById(recipeId)
    }

    val currentUserId =
        FirebaseAuth
            .getInstance()
            .currentUser
            ?.uid ?: ""

    val isLiked =
        recipe?.likedBy?.contains(currentUserId)
            ?: false

    val isConnected =
        isInternetAvailable(context)

    var isSaved by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(recipeId) {

        isSaved = viewModel.isRecipeSaved(
            recipeId,
            currentUserId
        )
    }

    val bahanList =
        recipe?.ingredients
            ?.split("\n")
            ?: emptyList()

    val langkahList =
        recipe?.steps
            ?.split("\n")
            ?: emptyList()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFFF9F3)
    ) {
        Scaffold(
            containerColor = Color(0xFFFFF9F3)
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFFFF9F3))
            ) {
                // ==========================================
                // 1. HEADER (STAY / TIDAK IKUT SCROLL)
                // ==========================================
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(Color(0xFFFF5722))
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Kembali",
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { navController.popBackStack() }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = "Detail Resep",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                // ==========================================
                // 2. BAGIAN ATAS RESEP (STAY / TIDAK IKUT SCROLL)
                // ==========================================
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                ) {
                    // CARD GAMBAR & INTERAKSI
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(12.dp)
                    ) {
                        if (!recipe?.imageBase64.isNullOrEmpty()) {

                            val imageBytes = Base64.decode(
                                recipe?.imageBase64,
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
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )

                        } else {

                            Image(
                                painter = painterResource(id = R.drawable.pizza),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector =
                                    if (isLiked)
                                        Icons.Filled.Favorite
                                    else
                                        Icons.Outlined.FavoriteBorder,
                                contentDescription = null,
                                tint =
                                    if (!isConnected)
                                        Color.Gray
                                    else if (isLiked)
                                        Color.Red
                                    else
                                        Color.Black,
                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable(enabled = isConnected) {

                                        viewModel.toggleLike(
                                            recipeId = recipeId,
                                            userId = currentUserId,
                                            isCurrentlyLiked = isLiked
                                        )

                                        viewModel.getRecipeById(recipeId)
                                    }
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = recipe?.likesCount.toString(),
                                fontSize = 13.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Icon(
                                Icons.Outlined.ChatBubbleOutline,
                                contentDescription = null,

                                tint =

                                    if (!isConnected)
                                        Color.Gray
                                    else
                                        Color.Black,

                                modifier = Modifier
                                    .size(20.dp)
                                    .clickable(

                                        enabled = isConnected

                                    ) {

                                        navController.navigate(
                                            "comment/$recipeId"
                                        )
                                    }
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text(
                                text = recipe?.commentsCount.toString(),
                                fontSize = 13.sp,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(

                                imageVector = if (isSaved)
                                    Icons.Filled.Bookmark
                                else
                                    Icons.Outlined.BookmarkBorder,

                                contentDescription = null,

                                tint =

                                    if (!isConnected)
                                        Color.LightGray

                                    else if (isSaved)
                                        Color(0xFFFF5722)

                                    else
                                        Color.Black,

                                modifier = Modifier
                                    .size(22.dp)
                                    .clickable(

                                        enabled = isConnected

                                    ) {

                                        if (isSaved) {

                                            viewModel.unsaveRecipe(
                                                recipeId = recipeId,
                                                userId = currentUserId
                                            )

                                            isSaved = false

                                        } else {

                                            recipe?.let {

                                                viewModel.saveRecipe(

                                                    SavedRecipeEntity(
                                                        recipeId = it.id,
                                                        userId = currentUserId,
                                                        title = it.title,
                                                        description = it.description,
                                                        imageBase64 = it.imageBase64
                                                    )
                                                )

                                                isSaved = true
                                            }
                                        }
                                    }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = recipe?.title ?: "", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe?.description ?: "",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    // Garis Halus Abu-abu Pembatas
                    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFEFEFEF)))
                }

                // ==========================================
                // 3. AREA KONTEN BAWAH (BISA DI-SCROLL)
                // ==========================================
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()) // Scroll ditaruh khusus di sini
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    // SEKSI BAHAN
                    Text(text = "Bahan", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(10.dp))
                    bahanList.forEach { bahan ->
                        DetailItemRow(text = bahan)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // SEKSI LANGKAH
                    Text(text = "Langkah Memasak", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(10.dp))
                    langkahList.forEach { langkah ->
                        DetailItemRow(text = langkah)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun DetailItemRow(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFFFD1C4), RoundedCornerShape(8.dp))
            .background(Color.White, RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFFFF5722), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 13.sp, color = Color.DarkGray, fontWeight = FontWeight.Medium, modifier = Modifier.weight(1f))
    }
}
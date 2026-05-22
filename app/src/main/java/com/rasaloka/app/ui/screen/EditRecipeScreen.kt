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
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.rasaloka.app.di.AppModule
import com.rasaloka.app.viewmodel.RecipeViewModel
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

@Composable
fun EditRecipeScreen(
    navController: NavController,
    recipeId: String
) {

    val context = LocalContext.current

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule
            .provideRecipeViewModelFactory(context)
    )

    LaunchedEffect(Unit) {
        viewModel.getRecipeById(recipeId)
    }

    val recipe by viewModel
        .selectedRecipe
        .collectAsState()

    var title by remember { mutableStateOf("") }

    var description by remember { mutableStateOf("") }

    var ingredients by remember { mutableStateOf("") }

    var steps by remember { mutableStateOf("") }

    var imageBase64 by remember {
        mutableStateOf("")
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.let {

            val inputStream =
                context.contentResolver.openInputStream(it)

            val bytes = inputStream?.use {
                it.readBytes()
            }

            imageBase64 = Base64.encodeToString(
                bytes,
                Base64.DEFAULT
            )
        }
    }

    LaunchedEffect(recipe) {

        recipe?.let {

            title = it.title
            description = it.description
            ingredients = it.ingredients
            steps = it.steps
            imageBase64 = it.imageBase64 ?: ""
        }
    }

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

                // ==========================================
                // KOTAK INPUT FOTO
                // ==========================================
                Column {
                    Text("Foto Makanan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))

                    val strokeColor = Color(0xFFFFD1C4)
                    val cornerRadius = 8.dp

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .background(Color.White, RoundedCornerShape(cornerRadius))
                            .drawBehind {
                                val strokeWidth = 1.dp.toPx()
                                val dashPathEffect = PathEffect.dashPathEffect(
                                    intervals = floatArrayOf(15f, 10f),
                                    phase = 0f
                                )

                                drawRoundRect(
                                    color = strokeColor,
                                    style = Stroke(
                                        width = strokeWidth,
                                        pathEffect = dashPathEffect
                                    ),
                                    cornerRadius = CornerRadius(cornerRadius.toPx())
                                )
                            }
                            .clickable {
                                galleryLauncher.launch("image/*")
                            }
                    ) {
                        if (imageBase64.isNotEmpty()) {

                            val imageBytes = Base64.decode(
                                imageBase64,
                                Base64.DEFAULT
                            )

                            val bitmap = BitmapFactory.decodeByteArray(
                                imageBytes,
                                0,
                                imageBytes.size
                            )

                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {

                                Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .background(Color.Black.copy(alpha = 0.5f))
                                        .padding(vertical = 6.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Ganti Foto",
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                        } else {

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AddAPhoto,
                                    contentDescription = "Tambah Foto",
                                    tint = Color(0xFFFF5722),
                                    modifier = Modifier.size(28.dp)
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = "Tambah Foto Makanan",
                                    color = Color.LightGray,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // 1. INPUT NAMA MAKANAN (Teks Hitam)
                Column {
                    Text("Nama Makanan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        placeholder = ""
                    )
                }

                // 2. INPUT DESKRIPSI
                Column {
                    Text("Deskripsi", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        placeholder = ""
                    )
                }

                // 3. INPUT BAHAN
                Column {
                    Text("Bahan", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        value = ingredients,
                        onValueChange = {
                            ingredients = it
                        },
                        placeholder = "",
                        isMultiLine = true
                    )
                }

                // 4. INPUT LANGKAH MEMASAK (Multi-line + Sudah Terisi Teks Hitam)
                Column {
                    Text("Langkah Memasak", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.Black)
                    Spacer(modifier = Modifier.height(6.dp))
                    EditInputField(
                        value = steps,
                        onValueChange = {
                            steps = it
                        },
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
                        onClick = {

                            recipe?.let {

                                val updatedRecipe = it.copy(
                                    title = title,
                                    description = description,
                                    ingredients = ingredients,
                                    steps = steps,
                                    imageBase64 = imageBase64
                                )

                                viewModel.updateRecipe(
                                    recipe = updatedRecipe,

                                    recipeEntity = com.rasaloka.app.data.local.entity.RecipeEntity(
                                        id = updatedRecipe.id,
                                        userId = updatedRecipe.userId,
                                        username = updatedRecipe.username,
                                        title = updatedRecipe.title,
                                        description = updatedRecipe.description,
                                        ingredients = updatedRecipe.ingredients,
                                        steps = updatedRecipe.steps,
                                        imageBase64 = updatedRecipe.imageBase64,
                                        likesCount = updatedRecipe.likesCount,
                                        commentsCount = updatedRecipe.commentsCount,
                                        createdAt = updatedRecipe.createdAt
                                    )
                                )

                                navController.popBackStack()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Simpan",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
    }
}

// Input Halaman Edit
@Composable
fun EditInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isMultiLine: Boolean = false
) {


    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
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
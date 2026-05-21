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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.rasaloka.app.di.AppModule
import com.rasaloka.app.viewmodel.RecipeViewModel
import com.rasaloka.app.data.local.entity.RecipeEntity
import java.util.UUID
import android.net.Uri
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import java.io.InputStream
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

@Composable
fun AddRecipeScreen(navController: NavController) {

    val context = LocalContext.current

    val viewModel: RecipeViewModel = viewModel(
        factory = AppModule
            .provideRecipeViewModelFactory(context)
    )

    // =========================
// STATE FORM
// =========================

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var ingredients by remember {
        mutableStateOf("")
    }

    var steps by remember {
        mutableStateOf("")
    }

    var imageBase64 by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            uri?.let {

                selectedImageUri = it

                val inputStream: InputStream? =
                    context.contentResolver.openInputStream(it)

                val bytes =
                    inputStream?.readBytes()

                inputStream?.close()

                if (bytes != null) {

                    imageBase64 = Base64.encodeToString(
                        bytes,
                        Base64.DEFAULT
                    )
                }
            }
        }

    Scaffold(
        // BOTTOM BAR (Bottom Navigation dengan Resep Saya tetap ACTIVE)
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
                .background(Color(0xFFFFF9F3)) // Background cream senada aplikasi
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(Color(0xFFFF5722))
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "Tambah Resep",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                // ==================
                // KOTAK INPUT FOTO
                // ==================
                Column {
                    FormLabel(text = "Foto Makanan")
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
                                    intervals = floatArrayOf(15f, 10f), // 15f panjang garis, 10f jarak spasi
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
                        if (selectedImageUri != null) {

                            AsyncImage(
                                model = selectedImageUri,
                                contentDescription = "Preview Foto",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                            )

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

                // 1. INPUT NAMA MAKANAN
                Column {
                    FormLabel(text = "Nama Makanan")
                    Spacer(modifier = Modifier.height(6.dp))
                    CustomInputField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        placeholder = "Contoh: Nasi Goreng Spesial"
                    )
                }

                // 2. INPUT DESKRIPSI
                Column {
                    FormLabel(text = "Deskripsi")
                    Spacer(modifier = Modifier.height(6.dp))
                    CustomInputField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        placeholder = "Contoh: Nasi Goreng Spesial"
                    )
                }

                // 3. INPUT BAHAN (Multi-line / Lebih Tinggi)
                Column {
                    FormLabel(text = "Bahan")
                    Spacer(modifier = Modifier.height(6.dp))
                    CustomInputField(
                        value = ingredients,
                        onValueChange = {
                            ingredients = it
                        },
                        placeholder = "Masukkan bahan-bahan\nContoh:\n3 Butir Telur\n200g daging ayam",
                        isMultiLine = true
                    )
                }

                // 4. INPUT LANGKAH MEMASAK (Multi-line / Lebih Tinggi)
                Column {
                    FormLabel(text = "Langkah Memasak")
                    Spacer(modifier = Modifier.height(6.dp))
                    CustomInputField(
                        value = steps,
                        onValueChange = {
                            steps = it
                        },
                        placeholder = "Masukkan langkah memasak",
                        isMultiLine = true
                    )
                }

                if (errorMessage.isNotEmpty()) {

                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // TOMBOL AKSI (Batal & Simpan Resep)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Tombol Batal
                    Button(
                        onClick = { navController.popBackStack() }, // Kembali ke halaman sebelumnya
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .border(1.dp, Color(0xFFFF5722), RoundedCornerShape(12.dp))
                    ) {
                        Text(
                            text = "Batal",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    // Tombol Simpan Resep
                    Button(
                        onClick = {

                            if (
                                title.isBlank() ||
                                description.isBlank() ||
                                ingredients.isBlank() ||
                                steps.isBlank()
                            ) {

                                errorMessage = "Semua field wajib diisi"

                                return@Button
                            }

                            errorMessage = ""

                            val recipeId = UUID.randomUUID().toString()

                            // =========================
                            // FIRESTORE MODEL
                            // =========================

                            val recipe = com.rasaloka.app.data.model.Recipe(
                                id = recipeId,
                                userId = "user_001",
                                username = "Amanda",
                                title = title,
                                description = description,
                                ingredients = ingredients,
                                steps = steps,
                                imageBase64 = imageBase64,
                                likesCount = 0,
                                commentsCount = 0,
                                createdAt = System.currentTimeMillis()
                            )

                            // =========================
                            // ROOM ENTITY
                            // =========================

                            val recipeEntity = RecipeEntity(
                                id = recipeId,
                                userId = "user_001",
                                username = "Amanda",
                                title = title,
                                description = description,
                                ingredients = ingredients,
                                steps = steps,
                                imageBase64 = imageBase64,
                                likesCount = 0,
                                commentsCount = 0,
                                createdAt = System.currentTimeMillis()
                            )

                            // =========================
                            // SIMPAN
                            // =========================

                            viewModel.addRecipe(
                                recipe,
                                recipeEntity
                            )

                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(
                            text = "Simpan Resep",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                append(text)
            }
            withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 14.sp)) {
                append( " *")
            }
        }
    )
}

@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isMultiLine: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = true,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.LightGray,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFD1C4),
            unfocusedBorderColor = Color(0xFFFFD1C4),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp),
        singleLine = !isMultiLine,
        minLines = if (isMultiLine) 4 else 1,
        maxLines = if (isMultiLine) 6 else 1,
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isMultiLine) 120.dp else 56.dp)
    )
}
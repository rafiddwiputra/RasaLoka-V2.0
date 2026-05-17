package com.rasaloka.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@Composable
fun ProfilScreen(navController: NavController) {

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

                // INPUT NAMA
                OutlinedTextField(
                    value = "Pawestri Wahyuning Gusti",
                    onValueChange = {},

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color.LightGray
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

                // INPUT EMAIL
                OutlinedTextField(
                    value = "PawestriWahyuning@gmail.com",
                    onValueChange = {},

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(16.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFFF5722),
                        unfocusedBorderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.height(28.dp))

                // BUTTON KELUAR
                Box(
                    contentAlignment = Alignment.Center,

                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            Color.Red,
                            RoundedCornerShape(14.dp)
                        )
                        .background(
                            Color(0xFFFFF9F3),
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { }
                        .padding(vertical = 14.dp)
                ) {

                    Text(
                        text = "Keluar",
                        color = Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // BUTTON SIMPAN
            Button(
                onClick = { },

                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5722)
                ),

                shape = RoundedCornerShape(16.dp),

                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 20.dp)
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
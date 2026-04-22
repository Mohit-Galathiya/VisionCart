package com.example.visioncart.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.visioncart.R
import com.example.visioncart.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(scrollBehavior: TopAppBarScrollBehavior, navController: NavController) {

    TopAppBar(
        modifier = Modifier.fillMaxWidth().background(color = Color.White),
        title = {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Mohit Galathiya",
                    color = Color.Black,
                    fontSize = 14.sp,
                    lineHeight = 16.sp
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.ProfileScreen)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.profileimage),
                    tint = Color.Unspecified,
                    contentDescription = "Navigate to Profile Screen",
                    modifier = Modifier.size(34.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Routes.WishListScreen)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Favourite",
                    modifier = Modifier.size(22.dp)
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.bell),
                    contentDescription = "Notification",
                    modifier = Modifier.size(22.dp)
                )
            }

            IconButton(onClick = {
                navController.navigate(Routes.CartScreen)
            }) {
                Icon(
                    painter = painterResource(R.drawable.cart),
                    contentDescription = "Cart",
                    modifier = Modifier.size(22.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

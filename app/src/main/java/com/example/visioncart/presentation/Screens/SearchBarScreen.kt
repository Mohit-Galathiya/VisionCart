package com.example.visioncart.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.visioncart.R
import com.example.visioncart.presentation.components.OutlineCardForSearchScreen
import com.example.visioncart.presentation.components.SearchBarForSearchScreen

@Composable
fun SearchBarScreen(
    navController: NavController
) {
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "back button"
                    )
                }
                SearchBarForSearchScreen(navController = navController)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                color = Color.LightGray,
                thickness = 0.5.dp
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 12.dp)
            ) {
                Text(
                    text = "Popular Searches",
                    modifier = Modifier.padding(horizontal = 12.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                        .padding(top = 10.dp)
                ) {
                    OutlineCardForSearchScreen(product = "shirts")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "hoodie")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "tshirt")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "kurti")
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                ) {
                    OutlineCardForSearchScreen(product = "hoodie Set")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "shirts Set")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "kurti Set")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "Kids Set")
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    OutlineCardForSearchScreen(product = "Formal Shirt")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "GenZ hoodie")
                    Spacer(modifier = Modifier.width(8.dp))

                    OutlineCardForSearchScreen(product = "trending kurti")
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.search_image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().padding(32.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SearchScreenPreview() {
    SearchBarScreen(navController = rememberNavController())
}

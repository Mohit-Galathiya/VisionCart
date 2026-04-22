package com.example.visioncart.presentation.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.visioncart.R
import com.example.visioncart.presentation.Utils.Banner
import com.example.visioncart.presentation.components.DetaildProductCard
import com.example.visioncart.presentation.components.DetaildProductItem
import com.example.visioncart.presentation.navigation.Routes
import com.example.visioncart.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisionCartMallScreen(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel(),
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    val productItems = listOf(
        DetaildProductItem(
            image = R.drawable.tshirt_red,
            productName = "Kids Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
        DetaildProductItem(
            image = R.drawable.hoodie_blue,
            productName = "Adult Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
        DetaildProductItem(
            image = R.drawable.kurti_pink,
            productName = "Women Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
        DetaildProductItem(
            image = R.drawable.shirts_black,
            productName = "Men Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
        DetaildProductItem(
            image = R.drawable.hoodie_gray,
            productName = "Adult Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
        DetaildProductItem(
            image = R.drawable.tshirt_black,
            productName = "Kids Wear",
            productPrice = "1000",
            discountedPrice = "500",
            discount = "50% off",
            rating = "4.5",
            ratingCount = "(12000)"
        ),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "VisionCart Mall",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchBarScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            modifier = Modifier.size(26.dp),
                            contentDescription = "Search"
                        )
                    }
                    IconButton(onClick = {
                        navController.navigate(Routes.WishListScreen)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.heart),
                            modifier = Modifier.size(22.dp),
                            contentDescription = "Wishlist"
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                    homeState.banners?.let { banners ->
                        Banner(banners = banners)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Budget Buys",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "View All",
                        color = Color.Red,
                        modifier = Modifier.clickable { /* TODO */ }
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.back),
                            modifier = Modifier
                                .size(16.dp)
                                .background(color = Color.Red, shape = CircleShape),
                            contentDescription = "View All",
                            tint = Color.White
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productItems) { product ->
                        DetaildProductCard(
                            product = product,
                            modifier = Modifier.width(160.dp)
                        )
                    }
                }
            }

            item {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = { Text(text = label) }
                        )
                    }
                }
            }

            items(productItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    rowItems.forEach { product ->
                        DetaildProductCard(
                            product = product,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

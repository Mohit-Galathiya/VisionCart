package com.example.visioncart.presentation.Screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.visioncart.R
import com.example.visioncart.domain.di.models.ProductDataModels
import com.example.visioncart.presentation.Utils.Banner
import com.example.visioncart.presentation.components.*
import com.example.visioncart.presentation.navigation.Routes
import com.example.visioncart.presentation.viewModels.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenUI(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    navController: NavController
) {
    val homeState by viewModel.homeScreenState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        snapAnimationSpec = spring(
            stiffness = Spring.StiffnessMedium,
        )
    )

    val listState = rememberLazyListState()

    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("Sort", "Category", "Gender", "Filters")

    LaunchedEffect(key1 = Unit) {
        viewModel.getAllSuggestedProducts()
    }

    if (homeState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else if (homeState.errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = homeState.errorMessage!!)
        }
    } else {

        val lowPriceItemsList = listOf(
            LowPriceCardItem(image = R.drawable.hoodie_red, price = "₹299", cardName = "Kids Boys"),
            LowPriceCardItem(image = R.drawable.hoodie_blue, price = "₹299", cardName = "Kids Girls"),
            LowPriceCardItem(image = R.drawable.kurti_pink, price = "₹299", cardName = "Kids Clothing"),
            LowPriceCardItem(image = R.drawable.tshirt_red, price = "₹299", cardName = "Jewellery"),
            LowPriceCardItem(image = R.drawable.kurti_yellow, price = "₹299", cardName = "Women Sarees")
        )

        val productItems = listOf(
            DetaildProductItem(
                image = R.drawable.hoodie_red,
                productName = "Adult Wear",
                productPrice = "500",
                discountedPrice = "250",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
            DetaildProductItem(
                image = R.drawable.hoodie_blue,
                productName = "Adult Wear",
                productPrice = "1000",
                discountedPrice = "500",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
            DetaildProductItem(
                image = R.drawable.tshirt_red,
                productName = "Kids Wear",
                productPrice = "500",
                discountedPrice = "250",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
            DetaildProductItem(
                image = R.drawable.kurti_pink,
                productName = "Women Wear",
                productPrice = "500",
                discountedPrice = "250",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
            DetaildProductItem(
                image = R.drawable.shirts_black,
                productName = "Men Wear",
                productPrice = "1000",
                discountedPrice = "500",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
            DetaildProductItem(
                image = R.drawable.shirts_white,
                productName = "Men Wear",
                productPrice = "500",
                discountedPrice = "250",
                discount = "50% off",
                rating = "4.5",
                ratingCount = "12700"
            ),
        )

        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                Column(modifier = Modifier.fillMaxWidth().background(Color.White)) {
                    TopBarComponent(scrollBehavior, navController)
                    SearchBarComponentForHomeScreen(navController)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        ) { innerPadding ->

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .padding(innerPadding)
            ) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            ViewAllCategoryItem(
                                onClick = {
                                    navController.navigate(Routes.CategoryScreen)
                                }
                            )
                        }

                        items(homeState.categories ?: emptyList()) { category ->
                            CategoryItem(
                                imageUrl = category.categoryImage,
                                categoryName = category.name,
                                onClick = {
                                    navController.navigate(
                                        Routes.EachCategoryItemScreen(
                                            categoryName = category.name
                                        )
                                    )
                                }
                            )
                        }
                    }
                }

                item {
                    Banner(banners = homeState.banners ?: emptyList())

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Flash Sale",
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray,
                                fontSize = 20.sp
                            )

                            Text(
                                text = "See more",
                                color = Color.Blue,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.clickable {
                                    navController.navigate(Routes.SeeAllProductScreen)
                                }
                            )
                        }

                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(homeState.products ?: emptyList()) { product ->
                                ProductCard(
                                    product = product,
                                    navController = navController
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = "Low Price Store",
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(lowPriceItemsList) { cardData ->
                                LowPriceCard(cardData = cardData)
                            }
                        }
                    }
                }

                item {
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
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
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
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
}

@Composable
fun ViewAllCategoryItem(onClick: () -> Unit) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.category),
                modifier = Modifier.padding(12.dp),
                contentDescription = "View All",
                tint = Color.Black
            )
        }
        Text("All", style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
fun CategoryItem(
    imageUrl: String,
    categoryName: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Text(
            text = categoryName,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ProductCard(product: ProductDataModels, navController: NavController) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable {
                navController.navigate(Routes.EachProductDetailsScreen(product.productId))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "₹${product.finalprice}",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${product.price}",
                        style = MaterialTheme.typography.bodySmall,
//                        textDecoration = TextDecoration.LineThrough,
                        color = Color.Black
                    )
                }
//                Text(
//                    text = "(${product.availableunits} left)",
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Red
//                )
            }
        }
    }
}

package com.example.visioncart.presentation.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.visioncart.R
import com.example.visioncart.presentation.components.CategoryProductCard
import com.example.visioncart.presentation.components.CategoryProductItem
import com.example.visioncart.presentation.navigation.Routes
import kotlinx.coroutines.launch

data class Category(
    val name: String,
    val iconsRes: Int
)

data class CategorySection(
    val category: Category,
    val products: List<CategoryProductItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController) {

    val categories = remember {
        listOf(
            Category("Popular", R.drawable.kurti_pink),
            Category("Kurti", R.drawable.kurti_yellow),
            Category("Women Western", R.drawable.hoodie_red),
            Category("Men", R.drawable.shirts_black),
            Category("Women", R.drawable.kurti_pink),
            Category("Kids", R.drawable.tshirt_red),
            Category("Seasonal", R.drawable.hoodie_blue),
            Category("adult", R.drawable.shirts_white),
            Category("t-shirt", R.drawable.tshirt_black),
            Category("Shirts", R.drawable.shirts),
        )
    }

    val allProducts = remember {
        listOf(
            CategoryProductItem(R.drawable.tshirt_red, "Kids wear", "Popular"),
            CategoryProductItem(R.drawable.kurti_pink, "Girls wear", "Popular"),
            CategoryProductItem(R.drawable.shirts_black, "Mens wear", "Men"),
            CategoryProductItem(R.drawable.kurti_yellow, "kurtis wear", "Kurti"),
            CategoryProductItem(R.drawable.shirts_white, "shirts", "Men"),
            CategoryProductItem(R.drawable.hoodie_gray, "hoodie", "adult"),
            CategoryProductItem(R.drawable.tshirt_black, "t-shirt", "Kids"),
            CategoryProductItem(R.drawable.hoodie_red, "Perfumes", "Popular"),
            CategoryProductItem(R.drawable.hoodie_blue, "summer", "Popular"),
            CategoryProductItem(R.drawable.shirts, "Men formal", "Popular"),
        )
    }

    val categoryIndexMap = remember { mutableMapOf<String, Int>() }

    val sections = remember {
        var currentItemIndex = 0
        categories.map { category ->
            categoryIndexMap[category.name] = currentItemIndex
            val filteredProducts = allProducts.filter { it.categoryName == category.name }
            currentItemIndex += 2
            CategorySection(category = category, products = filteredProducts)
        }
    }

    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedCategory) {
        categoryIndexMap[selectedCategory.name]?.let { index ->
            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CATEGORIES",
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
        Row(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CategorySidebar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it })

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                sections.forEach { section ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = section.category.name,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            )
                            HorizontalDivider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                thickness = 1.5.dp,
                                color = Color.LightGray
                            )
                        }
                    }

                    item {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 1000.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            userScrollEnabled = false
                        ) {
                            items(section.products) { product ->
                                CategoryProductCard(product = product)
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun CategorySidebar(
    categories: List<Category>,
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .width(80.dp)
            .shadow(elevation = 4.dp)
            .background(Color.White)
            .padding(bottom = 12.dp)
    ) {
        items(categories) { category ->
            CategoryItem(
                category = category,
                isSelected = category == selectedCategory,
                onClick = { onCategorySelected(category) }
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconsRes),
                    contentDescription = category.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = category.name,
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = Color.DarkGray,
                maxLines = 2,
                lineHeight = 14.sp,
                overflow = TextOverflow.Ellipsis
            )
        }

        if (isSelected) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(70.dp)
                    .background(
                        color = Color(0xFF28942B),
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    )
            )
        } else {
            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}

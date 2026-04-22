package com.example.visioncart.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.bottombar.AnimatedBottomBar
import com.example.visioncart.R
import com.example.visioncart.presentation.Screens.*
import com.google.firebase.auth.FirebaseAuth


data class BottomNavItem(
    val name: String,
    val icon: Painter,
    val unselectedIcon: Painter
)

@SuppressLint("UnUsedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(firebaseAuth: FirebaseAuth, payTest: () -> Unit) {

    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    var shouldShowBottomBar by remember { mutableStateOf(false) }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar = when (currentDestination) {
            Routes.HomeScreen::class.qualifiedName,
            Routes.CategoryScreen::class.qualifiedName,
            Routes.MyOrderScreen::class.qualifiedName,
            Routes.VisionCartMallScreen::class.qualifiedName -> true
            else -> false
        }
    }

    val bottomNavItemsList = listOf(
        BottomNavItem(
            "Home",
            icon = painterResource(R.drawable.home),
            unselectedIcon = painterResource(R.drawable.home)
        ),
        BottomNavItem(
            "Categories",
            icon = painterResource(R.drawable.category),
            unselectedIcon = painterResource(R.drawable.category)
        ),
        BottomNavItem(
            "Mall",
            icon = painterResource(R.drawable.mall),
            unselectedIcon = painterResource(R.drawable.mall)
        ),
        BottomNavItem(
            "My Orders",
            icon = painterResource(R.drawable.order),
            unselectedIcon = painterResource(R.drawable.order)
        ),
    )

    val startScreen: Any = if (firebaseAuth.currentUser == null) {
        SubNavigation.LoginSignUpScreen
    } else {
        SubNavigation.MainHomeScreen
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.navigationBars),
        bottomBar = {
            if (shouldShowBottomBar) {
                AnimatedBottomBar(
                    selectedItem = selectedItem,
                    itemSize = bottomNavItemsList.size,
                    containerColor = Color.White,
                ) {
                    bottomNavItemsList.forEachIndexed { index, navigationItem ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {
                                selectedItem = index
                                when (index) {
                                    0 -> navController.navigate(Routes.HomeScreen)
                                    1 -> navController.navigate(Routes.CategoryScreen)
                                    2 -> navController.navigate(Routes.VisionCartMallScreen)
                                    3 -> navController.navigate(Routes.MyOrderScreen)
                                }
                            },
                            label = {
                                Text(
                                    text = navigationItem.name,
                                    color = if (index == selectedItem) Color.Black else Color.Gray,
                                    fontSize = 10.sp
                                )
                            },
                            icon = {
                                Icon(
                                    painter = navigationItem.icon,
                                    contentDescription = navigationItem.name,
                                    modifier = Modifier.size(24.dp),
                                    tint = if (index == selectedItem) Color.Black else Color.Gray
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = if (shouldShowBottomBar) 45.dp else 0.dp)
        ) {
            NavHost(navController = navController, startDestination = startScreen) {
                navigation<SubNavigation.LoginSignUpScreen>(startDestination = Routes.LoginScreen) {
                    composable<Routes.LoginScreen> {
                        LoginScreenUI(navController = navController)
                    }
                    composable<Routes.SignUpScreen> {
                        SignUpScreenUI(navController = navController)
                    }
                }

                navigation<SubNavigation.MainHomeScreen>(startDestination = Routes.HomeScreen) {
                    composable<Routes.HomeScreen> {
                        HomeScreenUI(navController = navController)
                    }
                    composable<Routes.CategoryScreen> {
                        CategoryScreen(navController)
                    }
                    composable<Routes.VisionCartMallScreen> {
                        VisionCartMallScreen(navController)
                    }
                    composable<Routes.MyOrderScreen> {
                        MyOrderScreen(navController)
                    }
                    composable<Routes.ProfileScreen> {
                        ProfileScreen(firebaseAuth = firebaseAuth, navController = navController)
                    }
                    composable<Routes.WishListScreen> {
                        GetAllFav(navController = navController)
                    }
                    composable<Routes.CartScreen> {
                        CartScreenUi(navController = navController)
                    }
                    composable<Routes.SearchBarScreen> {
                        SearchBarScreen(navController)
                    }
                    composable<Routes.PayScreen> {
                        PayScreen(navController)
                    }
                    composable<Routes.SeeAllProductScreen> {
                        GetAllProducts(navController = navController)
                    }
                    composable<Routes.AllCategoriesScreen> {
                        AllCategoriesScreenUI(navController = navController)
                    }
                }

                composable<Routes.EachProductDetailsScreen> { backStackEntry ->
                    val product: Routes.EachProductDetailsScreen = backStackEntry.toRoute()
                    EachProductDetailsScreen(
                        productID = product.productID,
                        navController = navController
                    )
                }

                composable<Routes.EachCategoryItemScreen> { backStackEntry ->
                    val category: Routes.EachCategoryItemScreen = backStackEntry.toRoute()
                    EachCategoriesProductScreenUi(
                        categoryName = category.categoryName,
                        navController = navController
                    )
                }

                composable<Routes.CheckoutScreen> { backStackEntry ->
                    val product: Routes.CheckoutScreen = backStackEntry.toRoute()
                    CheckoutScreenUi(
                        productID = product.productId,
                        navController = navController,
                        pay = payTest
                    )
                }
            }
        }
    }
}

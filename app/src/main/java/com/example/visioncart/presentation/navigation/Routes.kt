package com.example.visioncart.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SubNavigation {

    @Serializable
    object LoginSignUpScreen : SubNavigation()

    @Serializable
    object MainHomeScreen : SubNavigation()

}


@Serializable
sealed class Routes {

    @Serializable
    object LoginScreen

    @Serializable
    object SignUpScreen

    @Serializable
    object HomeScreen

    @Serializable
    object CategoryScreen
    @Serializable
    object VisionCartMallScreen
    @Serializable
    object MyOrderScreen
    @Serializable
    object ProfileScreen
    @Serializable
    object WishListScreen
    @Serializable
    object CartScreen
    @Serializable
    object SearchBarScreen
    @Serializable
    data class CheckoutScreen(
        val productId : String
    )
    @Serializable
    object PayScreen
    @Serializable
    object SeeAllProductScreen
    @Serializable
    data class EachProductDetailsScreen(
        val productID : String
    )

    @Serializable
    object AllCategoriesScreen

    @Serializable
    data class EachCategoryItemScreen(
        val categoryName : String
    )

}


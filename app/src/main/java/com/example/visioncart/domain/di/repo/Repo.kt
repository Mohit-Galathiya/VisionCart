package com.example.visioncart.domain.di.repo

import android.net.Uri
import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.BannerDataModels
import com.example.visioncart.domain.di.models.CartDataModels
import com.example.visioncart.domain.di.models.CategoryDataModels
import com.example.visioncart.domain.di.models.ProductDataModels
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.models.UserDataParent
import kotlinx.coroutines.flow.Flow


interface Repo {

    fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>>
    fun getuserById(uid: String?) : Flow<ResultState<UserDataParent>>
    fun upDateUserData(userDataParent: UserDataParent): Flow<ResultState<String>>
    fun userProfileImage(uri: Uri) : Flow<ResultState<String>>
    fun getCategoriesInLimited() : Flow<ResultState<List<CategoryDataModels>>>
    fun getProductsInLimited() : Flow<ResultState<List<ProductDataModels>>>
    fun getAllProducts() : Flow<ResultState<List<ProductDataModels>>>
    fun getProductById(productId : String) : Flow<ResultState<ProductDataModels>>
    fun addToCart(cartDataModels: CartDataModels) : Flow<ResultState<String>>
    fun addToFav(productDataModels: ProductDataModels) : Flow<ResultState<String>>
    fun getallFav() : Flow<ResultState<List<ProductDataModels>>>
    fun getCart() : Flow<ResultState<List<CartDataModels>>>
    fun getAllCategories() : Flow<ResultState<List<CategoryDataModels>>>
    fun getCheckout(productId: String): Flow<ResultState<ProductDataModels>>
    fun getBanner(): Flow<ResultState<List<BannerDataModels>>>
    fun getSpecificCategory(categoryName: String): Flow<ResultState<List<ProductDataModels>>>
    fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModels>>>
    fun getSpecificCategoryItems(categoryName: String): kotlinx.coroutines.flow.Flow<com.example.visioncart.common.ResultState<kotlin.collections.List<com.example.visioncart.domain.di.models.ProductDataModels>>>
    fun callback(function: Any): Flow<ResultState<List<CategoryDataModels>>>


}
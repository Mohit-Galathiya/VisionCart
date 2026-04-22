package com.example.visioncart.presentation.viewModels

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.visioncart.common.HomeScreenState
import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.CartDataModels
import com.example.visioncart.domain.di.models.CategoryDataModels
import com.example.visioncart.domain.di.models.ProductDataModels
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.models.UserDataParent
import com.example.visioncart.domain.di.useCase.AddToFavUseCase
import com.example.visioncart.domain.di.useCase.AddtoCardUseCase
import com.example.visioncart.domain.di.useCase.CreateUserUseCase
import com.example.visioncart.domain.di.useCase.GetAllCategoryUseCase
import com.example.visioncart.domain.di.useCase.GetAllFavUseCase
import com.example.visioncart.domain.di.useCase.GetAllProductUseCase
import com.example.visioncart.domain.di.useCase.GetAllSuggestedProductsUseCase
import com.example.visioncart.domain.di.useCase.GetBannerUseCase
import com.example.visioncart.domain.di.useCase.GetCartUseCase
import com.example.visioncart.domain.di.useCase.GetSpecificCategoryItems
import com.example.visioncart.domain.di.useCase.GetUserUseCase
import com.example.visioncart.domain.di.useCase.LoginUserUseCase
import com.example.visioncart.domain.di.useCase.UpDateUserDataUseCase
import com.example.visioncart.domain.di.useCase.UserProfileImageUseCase
import com.example.visioncart.domain.di.useCase.getCategoryInLimit
import com.example.visioncart.domain.di.useCase.getProductById
import com.example.visioncart.domain.di.useCase.getProductsInLimitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingAppViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase,
    private val loginUserUseCase: LoginUserUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val upDateUserDataUseCase: UpDateUserDataUseCase,
    private val userProfileImageUseCase: UserProfileImageUseCase,
    private val getCategoryInLimit: getCategoryInLimit,
    private val getProductsInLimitUseCase: getProductsInLimitUseCase,
    private val addtoCardUseCase: AddtoCardUseCase,
    private val getProductByID: getProductById,
    private val addToFavUseCase: AddToFavUseCase,
    private val getAllFavUseCase: GetAllFavUseCase,
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getBannerUseCase: GetBannerUseCase,
    private val getSpecificCategoryItems: GetSpecificCategoryItems,
    private val getAllProductUseCase: GetAllProductUseCase,
    private val getAllSuggestedProductsUseCase: GetAllSuggestedProductsUseCase,
    private val getCartUseCase: GetCartUseCase
) : ViewModel() {

    private val _signUpScreenState = MutableStateFlow(SignUpScreenState())
    val signUpScreenState = _signUpScreenState.asStateFlow()

    private val _loginScreenState = MutableStateFlow(LoginScreenState())
    val loginScreenState = _loginScreenState.asStateFlow()

    private val _upDateScreenState = MutableStateFlow(UpDateScreenState())
    val upDateScreenState = _upDateScreenState.asStateFlow()

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    private val _userProfileImageState = MutableStateFlow(UploadUserProfileImageState())
    val userProfileImageState = _userProfileImageState.asStateFlow()

    private val _getProductByIDState = MutableStateFlow(GetProductByIDState())
    val getProductByIDState = _getProductByIDState.asStateFlow()

    private val _getAllFavState = MutableStateFlow(GetAllFavState())
    val getAllFavState = _getAllFavState.asStateFlow()

    private val _getAllProductState = MutableStateFlow(GetAllProductState())
    val getAllProductState = _getAllProductState.asStateFlow()

    private val _getCartState = MutableStateFlow(GetCartState())
    val getCartState = _getCartState.asStateFlow()

    private val _getAllCategoriesState = MutableStateFlow(GetAllCategoriesState())
    val getAllCategoriesState = _getAllCategoriesState.asStateFlow()

    private val _getSpecificCategoryItemState = MutableStateFlow(GetSpecificCategoryItemState())
    val getSpecificCategoryItemState = _getSpecificCategoryItemState.asStateFlow()

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    init {
        loadHomeScreenData()
    }

    fun loadHomeScreenData() {
        viewModelScope.launch {
            combine(
                getCategoryInLimit.getCategoryInLimited(),
                getProductsInLimitUseCase.getProductsInLimit(),
                getBannerUseCase.getBannerUseCase()
            ) { categoriesResult, productResult, bannerResult ->
                when {
                    categoriesResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = categoriesResult.message)
                    }
                    productResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = productResult.message)
                    }
                    bannerResult is ResultState.Error -> {
                        HomeScreenState(isLoading = false, errorMessage = bannerResult.message)
                    }
                    categoriesResult is ResultState.Success && productResult is ResultState.Success && bannerResult is ResultState.Success -> {
                        HomeScreenState(
                            isLoading = false,
                            categories = categoriesResult.data,
                            products = productResult.data,
                            banners = bannerResult.data
                        )
                    }
                    else -> HomeScreenState(isLoading = true)
                }
            }.collect { state ->
                _homeScreenState.value = state
            }
        }
    }

    fun registerUserWithEmailAndPassword(userData: UserData) {
        viewModelScope.launch {
            createUserUseCase.createUser(userData).collect { result ->
                _signUpScreenState.value = when (result) {
                    is ResultState.Error -> _signUpScreenState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _signUpScreenState.value.copy(isLoading = true)
                    is ResultState.Success -> _signUpScreenState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun loginUserWithEmailAndPassword(userData: UserData) {
        viewModelScope.launch {
            loginUserUseCase.loginUser(userData).collect { result ->
                _loginScreenState.value = when (result) {
                    is ResultState.Error -> _loginScreenState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _loginScreenState.value.copy(isLoading = true)
                    is ResultState.Success -> _loginScreenState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getUserById(uid: String) {
        viewModelScope.launch {
            getUserUseCase.getUserById(uid).collect { result ->
                _profileScreenState.value = when (result) {
                    is ResultState.Error -> _profileScreenState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _profileScreenState.value.copy(isLoading = true)
                    is ResultState.Success -> _profileScreenState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun upDateUserData(userDataParent: UserDataParent) {
        viewModelScope.launch {
            upDateUserDataUseCase.upDataUserData(userDataParent).collect { result ->
                _upDateScreenState.value = when (result) {
                    is ResultState.Error -> _upDateScreenState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _upDateScreenState.value.copy(isLoading = true)
                    is ResultState.Success -> _upDateScreenState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun userProfileImage(uri: Uri) {
        viewModelScope.launch {
            userProfileImageUseCase.userProfileImage(uri).collect { result ->
                _userProfileImageState.value = when (result) {
                    is ResultState.Error -> _userProfileImageState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _userProfileImageState.value.copy(isLoading = true)
                    is ResultState.Success -> _userProfileImageState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            getAllProductUseCase.getAllProduct().collect { result ->
                _getAllProductState.value = when (result) {
                    is ResultState.Error -> _getAllProductState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getAllProductState.value.copy(isLoading = true)
                    is ResultState.Success -> _getAllProductState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByID.getProductById(productId).collect { result ->
                _getProductByIDState.value = when (result) {
                    is ResultState.Error -> _getProductByIDState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getProductByIDState.value.copy(isLoading = true)
                    is ResultState.Success -> _getProductByIDState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun addToCart(cartDataModels: CartDataModels) {
        viewModelScope.launch {
            addtoCardUseCase.addtoCard(cartDataModels).collect { _ -> }
        }
    }

    fun addToFav(productDataModels: ProductDataModels) {
        viewModelScope.launch {
            addToFavUseCase.addtoFav(productDataModels).collect { _ -> }
        }
    }

    fun getallFav() {
        viewModelScope.launch {
            getAllFavUseCase.getAllFav().collect { result ->
                _getAllFavState.value = when (result) {
                    is ResultState.Error -> _getAllFavState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getAllFavState.value.copy(isLoading = true)
                    is ResultState.Success -> _getAllFavState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getCart() {
        viewModelScope.launch {
            getCartUseCase.getCart().collect { result ->
                _getCartState.value = when (result) {
                    is ResultState.Error -> _getCartState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getCartState.value.copy(isLoading = true)
                    is ResultState.Success -> _getCartState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getSpecificCategoryItems(categoryName: String) {
        viewModelScope.launch {
            getSpecificCategoryItems.getSpecificCategoryItems(categoryName).collect { result ->
                _getSpecificCategoryItemState.value = when (result) {
                    is ResultState.Error -> _getSpecificCategoryItemState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getSpecificCategoryItemState.value.copy(isLoading = true)
                    is ResultState.Success -> _getSpecificCategoryItemState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }

    fun getAllCategories() {
        viewModelScope.launch {
            getAllCategoryUseCase.getAllCategoriesUseCase().collect { result ->
                _getAllCategoriesState.value = when (result) {
                    is ResultState.Error -> _getAllCategoriesState.value.copy(isLoading = false, errorMessage = result.message)
                    is ResultState.Loading -> _getAllCategoriesState.value.copy(isLoading = true)
                    is ResultState.Success -> _getAllCategoriesState.value.copy(isLoading = false, userData = result.data)
                }
            }
        }
    }
    fun getBanners() {
        viewModelScope.launch {
            getBannerUseCase.getBannerUseCase().collect { result ->
                _homeScreenState.value = when (result) {
                    is ResultState.Error -> _homeScreenState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )

                    is ResultState.Loading -> _homeScreenState.value.copy(
                        isLoading = true
                    )

                    is ResultState.Success -> _homeScreenState.value.copy(
                        isLoading = false,
                        banners = result.data
                    )
                }
            }
        }
    }


    fun getAllSuggestedProducts() {
        viewModelScope.launch {
            getAllSuggestedProductsUseCase.getAllSuggestedProducts().collect { _ -> }
        }
    }
}

data class ProfileScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: UserDataParent? = null
)

data class SignUpScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class LoginScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UpDateScreenState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class UploadUserProfileImageState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: String? = null
)

data class GetProductByIDState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: ProductDataModels? = null
)

data class GetAllFavState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetAllProductState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

data class GetCartState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CartDataModels?> = emptyList()
)

data class GetAllCategoriesState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<CategoryDataModels?> = emptyList()
)

data class GetSpecificCategoryItemState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val userData: List<ProductDataModels?> = emptyList()
)

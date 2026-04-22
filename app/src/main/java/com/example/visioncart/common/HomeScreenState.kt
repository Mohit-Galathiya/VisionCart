package com.example.visioncart.common

import com.example.visioncart.domain.di.models.BannerDataModels
import com.example.visioncart.domain.di.models.CategoryDataModels
import com.example.visioncart.domain.di.models.ProductDataModels

data class HomeScreenState (

    val isLoading : Boolean = true,
    val errorMessage : String? = null,
    val products : List<ProductDataModels>? = null,
    val categories : List<CategoryDataModels>? = null,
    val banners : List<BannerDataModels>? = null

)
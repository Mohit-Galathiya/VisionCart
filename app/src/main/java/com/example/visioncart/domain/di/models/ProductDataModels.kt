package com.example.visioncart.domain.di.models

import kotlinx.serialization.Serializable

@Serializable
data class ProductDataModels(

    var productId : String = "",
    var name : String = "",
    var image : String = "",
    var price : String = "",
    var description : String = "",
    var category : String = "",
    var finalprice : String = "",
    var availableunits : Int = 0

)

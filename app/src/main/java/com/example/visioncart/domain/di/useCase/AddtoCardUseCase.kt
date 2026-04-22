package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.CartDataModels
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddtoCardUseCase @Inject constructor(private val repo: Repo) {

    fun addtoCard(cartDataModels: CartDataModels) : Flow<ResultState<String>> {

        return repo.addToCart(cartDataModels)
    }




}
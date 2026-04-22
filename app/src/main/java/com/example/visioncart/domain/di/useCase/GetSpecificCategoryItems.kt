package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.ProductDataModels
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSpecificCategoryItems @Inject constructor(private val repo: Repo) {

    fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> {

        return repo.getSpecificCategoryItems(categoryName)

    }
}
package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.CategoryDataModels
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getCategoryInLimit @Inject constructor(private val repo: Repo) {

    fun getCategoryInLimited() : Flow<ResultState<List<CategoryDataModels>>> {

        return repo.getCategoriesInLimited()
    }

    }

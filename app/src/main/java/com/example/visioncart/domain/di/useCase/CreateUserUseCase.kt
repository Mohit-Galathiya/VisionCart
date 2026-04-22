package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.CartDataModels
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(private val repo: Repo) {

    fun createUser(userData: UserData): Flow<ResultState<String>> {

        return repo.registerUserWithEmailAndPassword(userData)
    }




}
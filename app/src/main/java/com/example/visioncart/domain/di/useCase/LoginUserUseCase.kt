package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(private val repo: Repo) {

    fun loginUser(userData: UserData): Flow<ResultState<String>> {

        return repo.loginUserWithEmailAndPassword(userData)

    }
}
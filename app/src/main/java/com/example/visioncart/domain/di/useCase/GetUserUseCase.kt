package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.UserDataParent
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repo: Repo) {

    fun getUserById(uid: String?): Flow<ResultState<UserDataParent>> {

        return repo.getuserById(uid)
    }
}
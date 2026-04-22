package com.example.visioncart.domain.di.useCase

import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.models.UserDataParent
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpDateUserDataUseCase @Inject constructor(private val repo: Repo){

    fun upDataUserData(userDataParent: UserDataParent): Flow<ResultState<String>> {

        return repo.upDateUserData(userDataParent)

    }
}
package com.example.visioncart.domain.di.useCase

import android.net.Uri
import com.example.visioncart.common.ResultState
import com.example.visioncart.domain.di.repo.Repo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileImageUseCase @Inject constructor(private val repo: Repo) {

    fun userProfileImage(uri: Uri): Flow<ResultState<String>> {

        return repo.userProfileImage(uri)

    }

}
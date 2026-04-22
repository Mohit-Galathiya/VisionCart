package com.example.visioncart.domain.di

import com.example.visioncart.data.di.repo.RepoImpl
import com.example.visioncart.domain.di.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

class DomainModule {

    @Provides
    fun ProvideRepo(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): Repo {

        return RepoImpl(firebaseAuth, firebaseFirestore)
    }


}

//package com.example.visioncart.domain.di
//
//import com.example.visioncart.data.di.repo.RepoImpl
//import com.example.visioncart.domain.di.repo.Repo
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//class DomainModule {
//
//    // ADD THIS: Tells Hilt how to get FirebaseAuth
//    @Provides
//    @Singleton
//    fun provideFirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }
//
//    // ADD THIS: Tells Hilt how to get FirebaseFirestore
//    @Provides
//    @Singleton
//    fun provideFirebaseFirestore(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Provides
//    fun ProvideRepo(firebaseAuth: FirebaseAuth, firebaseFirestore: FirebaseFirestore): Repo {
//        return RepoImpl(firebaseAuth, firebaseFirestore)
//    }
//}

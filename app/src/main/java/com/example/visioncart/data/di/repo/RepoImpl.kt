package com.example.visioncart.data.di.repo

import android.net.Uri
import com.example.visioncart.common.ADDTOFAV
import com.example.visioncart.common.ADD_TO_CART
import com.example.visioncart.common.ResultState
import com.example.visioncart.common.USER_COLLECTION
import com.example.visioncart.domain.di.models.BannerDataModels
import com.example.visioncart.domain.di.models.CartDataModels
import com.example.visioncart.domain.di.models.CategoryDataModels
import com.example.visioncart.domain.di.models.ProductDataModels
import com.example.visioncart.domain.di.models.UserData
import com.example.visioncart.domain.di.models.UserDataParent
import com.example.visioncart.domain.di.repo.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class RepoImpl @Inject constructor(

    var firebaseAuth: FirebaseAuth, var firebaseFirestore: FirebaseFirestore
) : Repo {

    override fun registerUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)

            firebaseAuth.createUserWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        firebaseFirestore.collection(USER_COLLECTION)
                            .document(task.result.user?.uid.toString()).set(userData)
                            .addOnCompleteListener { firestoreTask ->

                                if (firestoreTask.isSuccessful) {

                                    trySend(ResultState.Success("User Registered Successfully and to Firestore"))
                                } else {
                                    if (firestoreTask.exception != null) {
                                        trySend(ResultState.Error(firestoreTask.exception?.localizedMessage.toString()))
                                    }
                                }
                            }


                    }

                }
            awaitClose {
                close()
            }
        }


    override fun loginUserWithEmailAndPassword(userData: UserData): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)

            firebaseAuth.signInWithEmailAndPassword(userData.email, userData.password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        trySend(ResultState.Success("User Login Successfully"))

                    } else {
                        if (task.exception != null) {
                            trySend(ResultState.Error(task.exception?.localizedMessage.toString()))
                        }
                    }
                }

            awaitClose {
                close()
            }
        }

    override fun getuserById(uid: String?): Flow<ResultState<UserDataParent>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection(USER_COLLECTION).document(uid.toString()).get().addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val data = task.result.toObject<UserData>()!!
                val userDataParent = UserDataParent(task.result.id, data)
                trySend(ResultState.Success(userDataParent))

            } else {
                if (task.exception != null) {
                    trySend(ResultState.Error(task.exception?.localizedMessage.toString()))

                }
            }
        }

        awaitClose {
            close()
        }


    }


    override fun upDateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {


            trySend(ResultState.Loading)

            firebaseFirestore.collection(USER_COLLECTION).document(userDataParent.nodeId)
                .update(userDataParent.userdata.toMap()).addOnCompleteListener { task ->


                    if (task.isSuccessful) {

                        trySend(ResultState.Success("User Data Updated Successfully"))
                    } else {

                        if (task.exception != null) {
                            trySend(ResultState.Error(task.exception?.localizedMessage.toString()))
                        }

                    }
                }

            awaitClose { close() }
        }

    override fun userProfileImage(uri: Uri): Flow<ResultState<String>> = callbackFlow {

        trySend(ResultState.Loading)

        FirebaseStorage.getInstance().reference.child("userProfileImage/${System.currentTimeMillis()} + ${firebaseAuth.currentUser?.uid}")
            .putFile(uri).addOnCompleteListener { task ->

                task.result.storage.downloadUrl.addOnSuccessListener { imageUri ->

                    trySend(ResultState.Success(imageUri.toString()))
                }

                if (task.exception != null) {
                    trySend(ResultState.Error(task.exception?.localizedMessage.toString()))

                }
            }

        awaitClose {
            close()

        }

    }

    override fun getCategoriesInLimited(): Flow<ResultState<List<CategoryDataModels>>> =
        callbackFlow {

            trySend(ResultState.Loading)

            firebaseFirestore.collection("categories").limit(7).get().addOnSuccessListener { querySnapshot ->

                val categories = querySnapshot.documents.mapNotNull { document ->
                    document.toObject<CategoryDataModels>()
                }
                trySend(ResultState.Success(categories))
            }.addOnFailureListener { exception ->

                trySend(ResultState.Error(exception.toString()))
            }

            awaitClose {
                close()
            }
        }


    override fun getProductsInLimited(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection("Products").limit(10).get().addOnSuccessListener { querySnapshot ->

            val products = querySnapshot.documents.mapNotNull { document ->
                document.toObject<ProductDataModels>()?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener { exception ->

            trySend(ResultState.Error(exception.toString()))
        }

        awaitClose {
            close()
        }


    }


    override fun getAllProducts(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {

        trySend(ResultState.Loading)

        firebaseFirestore.collection("Products").get().addOnSuccessListener { querySnapshot ->

            val products = querySnapshot.documents.mapNotNull { document ->

                document.toObject<ProductDataModels>()?.apply {

                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.toString()))
        }
        awaitClose { close() }
    }


    override fun getProductById(productId: String): Flow<ResultState<ProductDataModels>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").document(productId).get()
                .addOnSuccessListener { documentSnapshot ->
                    val product = documentSnapshot.toObject<ProductDataModels>()?.apply {
                        this.productId = documentSnapshot.id
                    }
                    if (product != null) {
                        trySend(ResultState.Success(product))
                    } else {
                        trySend(ResultState.Error("Product not found"))
                    }
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
                }
            awaitClose { close() }
        }

    override fun addToCart(cartDataModels: CartDataModels): Flow<ResultState<String>> = callbackFlow {
        
        trySend(ResultState.Loading)
        
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("User_Cart")
            .add(cartDataModels).addOnSuccessListener { 
                
                trySend(ResultState.Success("Product Added To Cart"))
            }.addOnFailureListener { exception ->  
                
                trySend(ResultState.Error(exception.toString()))
            }
        
        awaitClose {
            close()
        }
    }

    override fun addToFav(productDataModels: ProductDataModels): Flow<ResultState<String>> = callbackFlow {
        
        trySend(ResultState.Loading)
        
        firebaseFirestore.collection(ADDTOFAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav")
            .add(productDataModels).addOnSuccessListener {  
                
                trySend(ResultState.Success("Product Added To Fav"))
            }.addOnFailureListener { exception ->  
                
                trySend(ResultState.Error(exception.toString()))
            }
        
        awaitClose {  
            close()
        }
    }

    override fun getallFav(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {
        
        trySend(ResultState.Loading)
        
        firebaseFirestore.collection(ADDTOFAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav")
        
            .get().addOnSuccessListener { querySnapshot ->  
                
                val fav = querySnapshot.documents.mapNotNull { document ->
                    
                    document.toObject<ProductDataModels>()?.apply {
                        productId = document.id
                    }
                }
                trySend(ResultState.Success(fav))
            }.addOnFailureListener { exception -> 
                
                trySend(ResultState.Error(exception.toString()))
            }
        
        awaitClose {  
            close()
        }
    }

    override fun getCart(): Flow<ResultState<List<CartDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADD_TO_CART).document(firebaseAuth.currentUser!!.uid).collection("User_Cart")
            .get().addOnSuccessListener { querySnapshot ->
                val cartItems = querySnapshot.documents.mapNotNull { document ->
                    document.toObject<CartDataModels>()
                }
                trySend(ResultState.Success(cartItems))
            }.addOnFailureListener { exception ->
                trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
            }
        awaitClose { close() }
    }

    override fun getAllCategories(): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").get().addOnSuccessListener { querySnapshot ->
            val categories = querySnapshot.documents.mapNotNull { document ->
                document.toObject<CategoryDataModels>()
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
        }
        awaitClose { close() }
    }

    override fun getCheckout(productId: String): Flow<ResultState<ProductDataModels>> = callbackFlow {
        
        trySend(ResultState.Loading)
        
        firebaseFirestore.collection("Products").document(productId).get().addOnSuccessListener { documentSnapshot ->
            val product = documentSnapshot.toObject<ProductDataModels>()?.apply {
                this.productId = documentSnapshot.id
            }
            if (product != null) {
                trySend(ResultState.Success(product))
            } else {
                trySend(ResultState.Error("Product not found"))
            }
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
        }
        awaitClose { close() }
    }

    override fun getBanner(): Flow<ResultState<List<BannerDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("banner").get().addOnSuccessListener { querySnapshot ->
            val banner = querySnapshot.documents.mapNotNull { document ->
                document.toObject<BannerDataModels>()
            }
            trySend(ResultState.Success(banner))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
        }
        awaitClose { close() }
    }

    override fun getSpecificCategory(categoryName: String): Flow<ResultState<List<ProductDataModels>>> =
        callbackFlow {
            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").whereEqualTo("category", categoryName).get()
                .addOnSuccessListener { querySnapshot ->
                    val products = querySnapshot.documents.mapNotNull { document ->
                        document.toObject<ProductDataModels>()?.apply {
                            productId = document.id
                        }
                    }
                    trySend(ResultState.Success(products))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
                }
            awaitClose { close() }
        }

    override fun getAllSuggestedProducts(): Flow<ResultState<List<ProductDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(ADDTOFAV).document(firebaseAuth.currentUser!!.uid).collection("User_Fav").get().addOnSuccessListener { querySnapshot ->
            val products = querySnapshot.documents.mapNotNull { document ->
                document.toObject<ProductDataModels>()?.apply {
                    productId = document.id
                }
            }
            trySend(ResultState.Success(products))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
        }
        awaitClose { close() }
    }

    override fun getSpecificCategoryItems(categoryName: String): Flow<ResultState<List<ProductDataModels>>> =
        callbackFlow {

            trySend(ResultState.Loading)
            firebaseFirestore.collection("Products").whereEqualTo("category", categoryName).get()
                .addOnSuccessListener { querySnapshot ->
                    val products = querySnapshot.documents.mapNotNull { document ->
                        document.toObject<ProductDataModels>()?.apply {
                            productId = document.id
                        }
                    }
                    trySend(ResultState.Success(products))
                }.addOnFailureListener { exception ->
                    trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
                }
            awaitClose { close() }
        }

    override fun callback(function: Any): Flow<ResultState<List<CategoryDataModels>>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection("categories").get().addOnSuccessListener { querySnapshot ->
            val categories = querySnapshot.documents.mapNotNull { document ->
                document.toObject<CategoryDataModels>()
            }
            trySend(ResultState.Success(categories))
        }.addOnFailureListener { exception ->
            trySend(ResultState.Error(exception.localizedMessage ?: "Unknown Error"))
        }
        awaitClose { close() }
    }


}

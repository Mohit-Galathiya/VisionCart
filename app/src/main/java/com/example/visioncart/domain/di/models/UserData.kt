package com.example.visioncart.domain.di.models

data class UserData(

    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val address: String = "",
    val profileImage: String = "",
)

{

    fun toMap(): Map<String, Any> {

        val map = mutableMapOf<String, Any>()
        map["firstName"] = firstName
        map["lastName"] = lastName
        map["email"] = email
        map["phoneNumber"] = phoneNumber
        map["password"] = password
        map["address"] = address
        map["profileImage"] = profileImage
        return map

    }

}

data class UserDataParent(
    val nodeId : String = "",
    val userdata : UserData = UserData()
)

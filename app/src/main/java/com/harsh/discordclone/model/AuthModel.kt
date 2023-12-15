package com.harsh.discordclone.model

import com.google.firebase.firestore.DocumentSnapshot

data class AuthModel(
    val userName: String,
    val phone: String,
    val email: String,
    val password: String,
    var userId: String,
){
    companion object{
        fun parse(document: DocumentSnapshot): AuthModel {
            val userId = document.getString(USER_ID)!!
            val userName = document.getString(USER_NAME)!!
            val phone = document.getString(PHONE)!!
            val email = document.getString(EMAIL)!!
            val password = document.getString(PASSWORD)!!
            return AuthModel(userId, userName, phone, email, password)
        }

        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        const val PHONE = "phone"
        const val EMAIL = "email"
        const val PASSWORD = "password"
    }
}

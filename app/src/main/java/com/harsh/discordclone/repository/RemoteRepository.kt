package com.harsh.discordclone.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.harsh.discordclone.model.AuthModel
import com.harsh.discordclone.model.UserModel
import com.harsh.discordclone.remotedatabase.FirestoreDao
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val firestoreDao: FirestoreDao){
    suspend fun getAllUserAuth(): MutableList<AuthModel> {
        return firestoreDao.getAllUserAuth()
    }
    fun createAccountWithEmail(email: String, password: String): Task<AuthResult> {
        return firestoreDao.createAccountWithEmail(email, password)
    }
    fun addAuthModel(authModel: AuthModel): Task<DocumentReference> {
        return firestoreDao.addAuthModel(authModel)
    }
    fun addUserModel(userModel: UserModel): Task<Void> {
        return firestoreDao.addUserModel(userModel)
    }
}
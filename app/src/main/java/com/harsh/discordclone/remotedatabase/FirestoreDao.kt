package com.harsh.discordclone.remotedatabase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.harsh.discordclone.model.AuthModel
import com.harsh.discordclone.model.UserModel
import kotlinx.coroutines.tasks.await

class FirestoreDao {
    private val auth = Firebase.auth
    private val firestore = Firebase.firestore
    suspend fun getAllUserAuth(): MutableList<AuthModel> {
        val list = mutableListOf<AuthModel>()
        firestore.collection(AUTHENTICATION).get().await().documents.forEach {
            list.add(AuthModel.parse(it))
        }
        return list
    }

    fun createAccountWithEmail(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }
    fun addAuthModel(authModel: AuthModel): Task<DocumentReference> {
        return firestore.collection(AUTHENTICATION).add(authModel)
    }
    fun addUserModel(userModel: UserModel): Task<Void> {
        return firestore.collection(USER).document(userModel.userId).set(userModel)
    }

    companion object {
        const val TAG = "FirestoreDao.kt"
        private const val AUTHENTICATION = "authentication"
        private const val USER = "user"
    }
}
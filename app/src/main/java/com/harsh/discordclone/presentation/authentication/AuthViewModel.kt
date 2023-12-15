package com.harsh.discordclone.presentation.authentication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.harsh.discordclone.model.AuthModel
import com.harsh.discordclone.model.CountryCode
import com.harsh.discordclone.model.UserModel
import com.harsh.discordclone.repository.LocalRepository
import com.harsh.discordclone.repository.RemoteRepository
import com.harsh.discordclone.util.ResultCode
import com.harsh.discordclone.util.ResultData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val remoteRepository: RemoteRepository,
) :
    ViewModel() {
    private val auth = Firebase.auth

    var isPhoneAuth: Boolean = true
    private val _countryCode = MutableLiveData<CountryCode>()
    var email: String = ""
    var displayName: String? = null
    var userName: String? = null
    var password: String? = null
    var dob: Calendar = Calendar.getInstance()

    var phoneNumber: String = ""
    lateinit var phoneAuthVerificationId: String

    private val _isPhoneLogin = MutableLiveData<Boolean>()

    init {
        _isPhoneLogin.value = false
    }

    fun createAccount(): LiveData<ResultData<FirebaseUser>> {
        return flow {
            emit(ResultData.Loading)
            val displayName = displayName!!
            val userName = userName!!
            val password = password!!
            val dob = dob.timeInMillis
            val allUserAuth = remoteRepository.getAllUserAuth()

            if (allUserAuth.any {
                    it.userName == userName
                }) {
                emit(ResultData.Failed(ResultCode.EXISTING_USERNAME, "Username Exists."))
                return@flow
            }

            if (isPhoneAuth) {
                //TODO

            } else {
                if (allUserAuth.any {
                        it.email == email
                    }) {
                    emit(ResultData.Failed(ResultCode.EXISTING_EMAIL, "Email Exists."))
                    return@flow
                }

                val authResult = remoteRepository.createAccountWithEmail(email, password).await()
                if (authResult.user == null) {
                    emit(ResultData.Failed(ResultCode.OTHER, "Failed to create account"))
                    return@flow
                }
                val user = authResult.user!!
                val joinedAt = user.metadata?.creationTimestamp ?: System.currentTimeMillis()

                val authModel = AuthModel(userName, "", email, password, user.uid)
                remoteRepository.addAuthModel(authModel).await()

                val userModel = UserModel(user.uid, userName, email, "", displayName, dob, joinedAt)
                remoteRepository.addUserModel(userModel).await()

                emit(ResultData.Success(user))
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.i(TAG, ".catch: ${it.message}")
            emit(ResultData.Failed(ResultCode.OTHER, it.toString()))
        }.asLiveData()

    }

    fun login(userId: String, password: String): LiveData<ResultData<Any>> {
        return flow {
            emit(ResultData.Loading)

            if (_isPhoneLogin.value!!) {
                emit(ResultData.Success("Auth using Phone"))
                //TODO
            } else {
                val res = auth.signInWithEmailAndPassword(userId, password).await()
                emit(ResultData.Success(res.user!!))
            }
        }.catch {
            emit(ResultData.Failed(ResultCode.OTHER, it.message))
        }.asLiveData()
    }

    val countryCode: LiveData<CountryCode>
        get() = _countryCode

    fun updateCountryCode(countryCode: CountryCode) {
        _countryCode.value = countryCode
    }


    val isPhoneLogin: LiveData<Boolean>
        get() = _isPhoneLogin

    fun updateIsPhoneLogin(status: Boolean) {
        if (_isPhoneLogin.value != status) {
            _isPhoneLogin.value = status
        }
    }

    fun getCountryCodes(): LiveData<List<CountryCode>> {
        return localRepository.getCountryCodes()
    }

    companion object {
        const val TAG = "AuthViewModel.kt"
    }
}
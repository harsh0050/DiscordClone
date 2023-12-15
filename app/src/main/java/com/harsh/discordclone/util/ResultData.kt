package com.harsh.discordclone.util

sealed class ResultData<out T> {
    data object Loading: ResultData<Nothing>()
    data class Success<out T>(val data: T): ResultData<T>()
    data class Failed(val resultCode: Int, val message: String? = null): ResultData<Nothing>()
}
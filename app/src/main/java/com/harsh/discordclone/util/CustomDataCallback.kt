package com.harsh.discordclone.util

fun interface CustomDataCallback<T> {
    fun onCallback(data: T)
}
package com.harsh.discordclone.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("user")
data class UserModel(
    @PrimaryKey val userId: String,
    val userName: String,
    val email: String,
    val phone: String,
    val displayName: String,
    val dob: Long,
    val joinedAt: Long,
    val dmIds: List<String> = ArrayList(),
    val channelIds: List<String> = ArrayList(),
    val profilePicture: List<Byte> = listOf(),
){
    companion object {
        const val USER_ID = "userId"
        const val USER_NAME = "userName"
        const val EMAIL = "email"
        const val PHONE = "phone"
        const val DISPLAY_NAME = "displayName"
        const val DOB = "dob"
        const val JOINED_AT = "joinedAt"
        const val DM_IDS = "dmIds"
        const val CHANNEL_IDS = "channelIds"
        const val PROFILE_PICTURE = "profilePicture"
    }
}

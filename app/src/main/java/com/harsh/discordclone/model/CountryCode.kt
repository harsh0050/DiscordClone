package com.harsh.discordclone.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_code_table")
data class CountryCode(
    @ColumnInfo("name") val name: String,
    @ColumnInfo("dial_code") val dialCode: Int,
    @ColumnInfo("code") @PrimaryKey val code: String
)
package com.harsh.discordclone.localdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.harsh.discordclone.model.CountryCode

@Dao
interface CountryCodeDao {
    @Query("SELECT * FROM country_code_table")
    fun getCountryCodes(): LiveData<List<CountryCode>>
}
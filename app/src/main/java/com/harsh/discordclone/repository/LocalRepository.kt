package com.harsh.discordclone.repository

import androidx.lifecycle.LiveData
import com.harsh.discordclone.localdatabase.dao.CountryCodeDao
import com.harsh.discordclone.model.CountryCode
import javax.inject.Inject

class LocalRepository @Inject constructor(private val countryCodeDao: CountryCodeDao) {
    fun getCountryCodes(): LiveData<List<CountryCode>> {
        return countryCodeDao.getCountryCodes()
    }
}
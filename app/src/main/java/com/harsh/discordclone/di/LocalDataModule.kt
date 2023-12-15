package com.harsh.discordclone.di

import android.content.Context
import com.harsh.discordclone.localdatabase.AppPreference
import com.harsh.discordclone.localdatabase.RoomDB
import com.harsh.discordclone.localdatabase.dao.CountryCodeDao
import com.harsh.discordclone.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class LocalDataModule {
    @Provides
    fun provideAppPreference(context: Context): AppPreference = AppPreference(context)

    @Provides
    fun provideRoomDb(context: Context): RoomDB {
        return RoomDB.getInstance(context)
    }

    @Provides
    fun provideCountryCodeDao(roomDB: RoomDB): CountryCodeDao {
        return roomDB.getCountryCodeDao()
    }

    @Provides
    fun provideLocalRepository(countryCodeDao: CountryCodeDao): LocalRepository {
        return LocalRepository(countryCodeDao)
    }
}
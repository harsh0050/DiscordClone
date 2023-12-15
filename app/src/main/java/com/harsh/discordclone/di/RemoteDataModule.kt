package com.harsh.discordclone.di

import com.harsh.discordclone.remotedatabase.FirestoreDao
import com.harsh.discordclone.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class RemoteDataModule {
    @Provides
    fun provideFirestoreDao() : FirestoreDao = FirestoreDao()

    @Provides
    fun provideRemoteRepository(firestoreDao: FirestoreDao) = RemoteRepository(firestoreDao)
}
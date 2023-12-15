package com.harsh.discordclone.presentation.mainscreen

import androidx.lifecycle.ViewModel
import com.harsh.discordclone.repository.LocalRepository
import com.harsh.discordclone.repository.RemoteRepository
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModel() {

}
package com.psychojean.gitpie.ui.repositories

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.data.enteties.User
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource

class RepositoriesViewModel @ViewModelInject constructor(
    private val repository: GitRepository
): ViewModel() {
    private val _accessToken = MutableLiveData<String>()

    private val _repositories = _accessToken.switchMap { accessToken ->
        repository.getAllRepos(accessToken)
    }

    val repositories: LiveData<Resource<List<Repo>>> = _repositories


    fun start(accessToken: String) {
        _accessToken.value = accessToken
    }
}
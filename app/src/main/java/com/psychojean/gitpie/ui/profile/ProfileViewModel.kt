package com.psychojean.gitpie.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.psychojean.gitpie.data.enteties.Organization
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.data.enteties.Starred
import com.psychojean.gitpie.data.enteties.User
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource
import kotlinx.coroutines.launch

class ProfileViewModel @ViewModelInject constructor(
    private val repository: GitRepository
): ViewModel() {
    private val _accessToken = MutableLiveData<String>()

    private val _user = _accessToken.switchMap {
        repository.getUser(it)
    }
    val user: LiveData<Resource<User>> = _user

    private val _repositories = _accessToken.switchMap {
        repository.getAllRepos(it)
    }
    val repositories: LiveData<Resource<List<Repo>>> = _repositories

    private val _starred = _accessToken.switchMap {
        repository.getStarred(it)
    }
    val starred: LiveData<Resource<List<Repo>>> = _starred

    private val _organizations = _accessToken.switchMap {
        repository.getOrganizations(it)
    }
    val organizations: LiveData<Resource<List<Organization>>> = _organizations

    fun start(accessToken: String) {
        viewModelScope.launch {
            _accessToken.value = accessToken
        }
    }
}
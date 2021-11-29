package com.psychojean.gitpie.ui.starred

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.data.enteties.Starred
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class StarredViewModel@ViewModelInject constructor(
    private val repository: GitRepository
): ViewModel() {
    private val _accessToken = MutableLiveData<String>()

    private val _starred = _accessToken.switchMap {
        repository.getStarred(it)
    }
    val starred: LiveData<Resource<List<Repo>>> = _starred

    fun start(accessToken: String) {
        viewModelScope.launch {
            _accessToken.value = accessToken
        }
    }
}
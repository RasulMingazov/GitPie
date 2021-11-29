package com.psychojean.gitpie.ui.authtorization

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.psychojean.gitpie.BuildConfig
import com.psychojean.gitpie.data.enteties.AccessToken
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource

class AuthorizationViewModel @ViewModelInject constructor(
        private val repository: GitRepository
): ViewModel() {

    private val _code = MutableLiveData<String>()

    private val _token = _code.switchMap { code ->
        repository.getAccessToken(BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET, code)
    }

    val accessToken: LiveData<Resource<AccessToken>> = _token

    fun start(code: String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
        _code.value = code
//            }
//        }
    }
}
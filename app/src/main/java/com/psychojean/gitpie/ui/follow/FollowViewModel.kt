package com.psychojean.gitpie.ui.follow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.psychojean.gitpie.data.enteties.Follow
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource
import kotlinx.coroutines.launch

class FollowViewModel @ViewModelInject constructor(
    private val repository: GitRepository
): ViewModel() {

    private val _params = MutableLiveData<Pair<String, String>>()


    private val _following = _params.switchMap {
        with (_params) {
            repository.getFollowing(this.value!!.first, this.value!!.second)
        }
    }
    private val _followers = _params.switchMap {
        with (_params) {
            repository.getFollowers(this.value!!.first, this.value!!.second)
        }
    }

    val following: LiveData<Resource<List<Follow>>> = _following
    val followers: LiveData<Resource<List<Follow>>> = _followers

    fun start(params: Pair<String, String>) {
        viewModelScope.launch {
            _params.value = params
        }
    }
}
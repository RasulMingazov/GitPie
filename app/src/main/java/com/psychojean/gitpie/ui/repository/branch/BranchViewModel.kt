package com.psychojean.gitpie.ui.repository.branch

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.psychojean.gitpie.data.enteties.Branch
import com.psychojean.gitpie.data.enteties.Repo
import com.psychojean.gitpie.data.repository.GitRepository
import com.psychojean.gitpie.utils.Resource
import kotlinx.coroutines.launch

class BranchViewModel @ViewModelInject constructor(
    private val gitRepository: GitRepository
): ViewModel() {

    private val _params = MutableLiveData<Triple<String, String, String>>()

    private val _branches = _params.switchMap {
        with(_params) {
            gitRepository.getBranches(this.value!!.first, this.value!!.second, this.value!!.third)
        }
    }

    val branches: LiveData<Resource<List<Branch>>> = _branches

    fun start(params: Triple<String, String, String>) {
        viewModelScope.launch {
            _params.value = params
        }
    }
}
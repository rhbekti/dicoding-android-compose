package com.rhbekti.stikacompose.ui.screen.home

import androidx.lifecycle.ViewModel
import com.rhbekti.stikacompose.data.Result
import com.rhbekti.stikacompose.data.UserRepository
import com.rhbekti.stikacompose.data.remote.response.UserItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(private val repository: UserRepository) : ViewModel() {

    private val _query: MutableStateFlow<String> = MutableStateFlow("")
    val query: StateFlow<String> get() = _query

    val userList: Flow<Result<List<UserItem>>> = _query.flatMapLatest {
        repository.searchUserByUsername(it)
    }

    fun searchUserByUsername(q: String) {
        _query.value = q
    }
}
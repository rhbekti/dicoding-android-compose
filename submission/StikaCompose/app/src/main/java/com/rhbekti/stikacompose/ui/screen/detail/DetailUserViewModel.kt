package com.rhbekti.stikacompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhbekti.stikacompose.data.Result
import com.rhbekti.stikacompose.data.UserRepository
import com.rhbekti.stikacompose.data.local.entity.UserEntity
import com.rhbekti.stikacompose.data.remote.response.DetailUserResponse
import com.rhbekti.stikacompose.data.remote.response.UserRepoResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DetailUserViewModel(private val repository: UserRepository) : ViewModel() {

    private var _userData: MutableStateFlow<Result<DetailUserResponse>> =
        MutableStateFlow(Result.Loading)
    val userData: StateFlow<Result<DetailUserResponse>> get() = _userData

    private var _userRepos: MutableStateFlow<Result<List<UserRepoResponseItem>>> =
        MutableStateFlow(Result.Loading)

    val userRepos: StateFlow<Result<List<UserRepoResponseItem>>> get() = _userRepos

    fun getDetailUserByUsername(username: String) {
        viewModelScope.launch {
            repository.getDetailUserById(username).catch {
                _userData.value = Result.Error(it.message.toString())
            }.collect {
                _userData.value = it
            }
        }
    }

    fun getUserReposByUsername(username: String) {
        viewModelScope.launch {
            repository.getUserRepo(username).catch {
                _userRepos.value = Result.Error(it.message.toString())
            }.collect {
                _userRepos.value = it
            }
        }
    }

    fun isFavoriteUser(username: String): Flow<Boolean> = repository.isFavoriteUser(username)

    fun saveUserAsFavorite(userEntity: UserEntity) {
        viewModelScope.launch {
            repository.saveUserAsFavorite(userEntity)
        }
    }

    fun deleteFromFavorite(user: UserEntity) {
        viewModelScope.launch {
            repository.deleteFromFavorite(user)
        }
    }
}
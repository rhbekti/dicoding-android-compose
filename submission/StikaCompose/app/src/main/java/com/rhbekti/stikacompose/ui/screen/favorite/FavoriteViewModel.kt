package com.rhbekti.stikacompose.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhbekti.stikacompose.data.UserRepository
import com.rhbekti.stikacompose.data.local.entity.UserEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: UserRepository) : ViewModel() {

    private val _userFavorites = MutableStateFlow(listOf<UserEntity>())
    val userFavorites: StateFlow<List<UserEntity>> = _userFavorites

    init {
        getAllFavoriteUsers()
    }

    private fun getAllFavoriteUsers() {
        viewModelScope.launch {
            repository.getAllFavoriteUsers().collect {
                _userFavorites.value = it
            }
        }
    }

}
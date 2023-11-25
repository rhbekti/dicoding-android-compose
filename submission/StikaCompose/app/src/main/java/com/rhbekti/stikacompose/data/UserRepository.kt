package com.rhbekti.stikacompose.data

import com.rhbekti.stikacompose.data.local.entity.UserEntity
import com.rhbekti.stikacompose.data.local.room.UserDao
import com.rhbekti.stikacompose.data.remote.retrofit.ApiService
import com.rhbekti.stikacompose.data.remote.response.DetailUserResponse
import com.rhbekti.stikacompose.data.remote.response.UserItem
import com.rhbekti.stikacompose.data.remote.response.UserRepoResponseItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserRepository constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    fun searchUserByUsername(q: String): Flow<Result<List<UserItem>>> = flow {
        emit(Result.Loading)
        try {
            val users = apiService.searchUser(q).items
            emit(Result.Success(users))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getDetailUserById(username: String): Flow<Result<DetailUserResponse>> = flow {
        emit(Result.Loading)
        try {
            val user = apiService.getDetailUser(username)
            emit(Result.Success(user))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserRepo(username: String): Flow<Result<List<UserRepoResponseItem>>> = flow {
        emit(Result.Loading)
        try {
            val userRepos = apiService.getUserRepo(username)
            emit(Result.Success(userRepos))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun saveUserAsFavorite(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }

    fun isFavoriteUser(username: String): Flow<Boolean> = userDao.isFavoriteUser(username)

    fun getAllFavoriteUsers(): Flow<List<UserEntity>> = userDao.getAllUsers()

    suspend fun deleteFromFavorite(userEntity: UserEntity) {
        userDao.delete(userEntity)
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository = instance ?: synchronized(this) {
            UserRepository(
                apiService, userDao
            ).apply { instance = this }
        }
    }
}
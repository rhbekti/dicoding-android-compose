package com.rhbekti.stikacompose.di

import android.content.Context
import com.rhbekti.stikacompose.data.UserRepository
import com.rhbekti.stikacompose.data.local.room.UserRoomDatabase
import com.rhbekti.stikacompose.data.remote.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val database = UserRoomDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService,dao)
    }
}
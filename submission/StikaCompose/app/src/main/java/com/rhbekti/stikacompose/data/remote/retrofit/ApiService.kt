package com.rhbekti.stikacompose.data.remote.retrofit

import com.rhbekti.stikacompose.data.remote.response.DetailUserResponse
import com.rhbekti.stikacompose.data.remote.response.UserRepoResponseItem
import com.rhbekti.stikacompose.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") q: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String
    ): DetailUserResponse

    @GET("users/{username}/repos")
    suspend fun getUserRepo(
        @Path("username") username: String
    ): List<UserRepoResponseItem>
}
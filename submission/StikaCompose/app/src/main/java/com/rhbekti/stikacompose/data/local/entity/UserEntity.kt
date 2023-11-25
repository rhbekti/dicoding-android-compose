package com.rhbekti.stikacompose.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "users")
@Parcelize
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo("avatar_url")
    var avatarUrl: String,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean

) : Parcelable
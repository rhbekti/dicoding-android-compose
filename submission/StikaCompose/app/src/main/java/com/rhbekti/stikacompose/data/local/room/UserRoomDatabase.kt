package com.rhbekti.stikacompose.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rhbekti.stikacompose.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): UserRoomDatabase {
            if (INSTANCE == null) {
                synchronized(UserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java, "stika_database"
                    )
                        .build()
                }
            }
            return INSTANCE as UserRoomDatabase
        }
    }
}
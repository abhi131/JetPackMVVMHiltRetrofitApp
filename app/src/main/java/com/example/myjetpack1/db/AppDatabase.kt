package com.example.myjetpack1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myjetpack1.model.User

@Database(entities = [User::class, RemoteKeys::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}

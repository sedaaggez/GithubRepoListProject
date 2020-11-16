package com.info.githubrepo.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.info.githubrepo.model.Owner
import com.info.githubrepo.model.Repo


@Database(entities = arrayOf(Repo::class), version = 1)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun repoDao() : RepoDao

    companion object {
        @Volatile private var instance : RepoDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
                context.applicationContext, RepoDatabase::class.java, "repodatabase"
        ).build()
    }
}
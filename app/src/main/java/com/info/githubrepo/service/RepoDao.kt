package com.info.githubrepo.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.info.githubrepo.model.Repo

@Dao
interface RepoDao {
    @Insert
    suspend fun insertAll(vararg repos: Repo): List<Long>

    @Query("SELECT * FROM repo")
    suspend fun getAllRepos(): List<Repo>

    @Query("SELECT * FROM repo WHERE uuid = :repoId")
    suspend fun getRepo(repoId: Int): Repo

    @Query("DELETE FROM repo")
    suspend fun deleteAllRepos()




}
package com.info.githubrepo.service

import com.info.githubrepo.model.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoAPI {

    // https://api.github.com/users/sedaaggez/repos

    @GET("users/{userName}/repos")
    fun getUserRepos(@Path("userName") userName: String): Single<List<Repo>>


}
package com.info.githubrepo.service

import com.info.githubrepo.model.Repo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RepoAPIService {

    private val BASE_URL = "https://api.github.com/"
    private val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RepoAPI::class.java)

    fun getData(userName: String) : Single<List<Repo>> {
        return api.getUserRepos(userName)
    }
}
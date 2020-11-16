package com.info.githubrepo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.info.githubrepo.model.Repo
import com.info.githubrepo.service.RepoDatabase
import kotlinx.coroutines.launch

class RepoDetailViewModel(application: Application) : BaseViewModel(application) {
    val repoLiveData = MutableLiveData<Repo>()

    fun getDataFromRoom(uuid: Int) {

        launch {
            val dao = RepoDatabase(getApplication()).repoDao()
            val repo = dao.getRepo(uuid)

            repoLiveData.value = repo
        }

    }
}
package com.info.githubrepo.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.info.githubrepo.model.Repo
import com.info.githubrepo.util.CustomSharedPreferences
import com.info.githubrepo.service.RepoAPIService
import com.info.githubrepo.service.RepoDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ReposViewModel(application: Application) : BaseViewModel(application) {

    private val repoAPIService = RepoAPIService()
    private val disposable = CompositeDisposable()

    val repos = MutableLiveData<List<Repo>>()
    val repoError = MutableLiveData<Boolean>()
    val repoLoading = MutableLiveData<Boolean>()

    fun init() {
        repoError.value = false
        repoLoading.value = false
    }


    fun getRepos(userName: String) {
        getDataFromAPI(userName)

    }

    private fun getDataFromAPI(userName: String) {
        repoLoading.value = true
        disposable.add(
            repoAPIService.getData(userName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Repo>>() {
                    override fun onSuccess(t: List<Repo>) {
                        storeInSQLite(t)
//                        Toast.makeText(getApplication(), "Repos From API", Toast.LENGTH_SHORT).show()

                    }

                    override fun onError(e: Throwable) {
                        repoError.value = true
                        repoLoading.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun showRepos(repoList: List<Repo>) {
        repos.value = repoList
        repoError.value = false
        repoLoading.value = false
    }

    private fun storeInSQLite(list: List<Repo>) {
        launch {
            val dao = RepoDatabase(getApplication()).repoDao()
            dao.deleteAllRepos()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i < list.size) {
                list[i].uuid = listLong[i].toInt()
                i += 1
            }
            showRepos(list)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}
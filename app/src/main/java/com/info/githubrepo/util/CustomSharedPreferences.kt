package com.info.githubrepo.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.info.githubrepo.model.Repo

class CustomSharedPreferences {

    companion object {
        private val PREFERENCES_FAVORITE_REPO = "preferences_favorite_repo"
        private var sharedPreferences: SharedPreferences? = null

        @Volatile private var instance: CustomSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) : CustomSharedPreferences = instance ?: synchronized(
            lock
        )  {
            instance ?: makeCustomSharedPreferences(context).also {
                instance = it
            }
        }

        private fun makeCustomSharedPreferences(context: Context) : CustomSharedPreferences {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()
        }
    }

    fun getFavoriteRepo() = sharedPreferences?.getLong(PREFERENCES_FAVORITE_REPO, 0)

    fun saveFavoriteRepo(repoUuid: String) {
        sharedPreferences?.edit(commit = true) {
            putString(PREFERENCES_FAVORITE_REPO, repoUuid)
        }
    }
}
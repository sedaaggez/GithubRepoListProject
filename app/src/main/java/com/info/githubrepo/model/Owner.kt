package com.info.githubrepo.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class Owner(
        @SerializedName("login")
        @ColumnInfo(name = "login")
        val userName: String?,
        @SerializedName("avatar_url")
        val userAvatarUrl: String?)
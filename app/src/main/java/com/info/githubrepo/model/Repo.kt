package com.info.githubrepo.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repo(
        @ColumnInfo(name = "name")
        @SerializedName("name")
        val repoName: String?,
        @SerializedName("owner")
        @Embedded val owner: Owner?,
        @ColumnInfo(name = "stargazers_count")
        @SerializedName("stargazers_count")
        val repoStargazersCount: String?,
        @ColumnInfo(name = "open_issues_count")
        @SerializedName("open_issues_count")
        val repoOpenIssuesCount: String?) {

        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0
}
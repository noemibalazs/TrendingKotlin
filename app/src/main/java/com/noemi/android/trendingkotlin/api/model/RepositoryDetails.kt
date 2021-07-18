package com.noemi.android.trendingkotlin.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("size") val size: Int,
    @SerializedName("stargazers_count") val stargazersCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("open_issues_count") val openIssuesCount: Int,
    @SerializedName("updated_at") val updated: String
) : Parcelable {
    override fun toString(): String {
        return "RepositoryDetails: id=$id, name='$name', description='$description', size=$size, stargazers_count=$stargazersCount, forks_count=$forksCount, open_issues_count=$openIssuesCount, updated: $updated"
    }

}
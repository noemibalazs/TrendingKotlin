package com.noemi.android.trendingkotlin.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repository(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String?,
    @SerializedName("updated_at") val updated: String,
    @SerializedName("stargazers_count") val stargazersCount: Int
) : Parcelable {

    override fun toString(): String {
        return "Repository:id=$id, name='$name', full_name='$fullName', description='$description', updated='$updated', watchers=$stargazersCount"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Repository

        if (id != other.id) return false
        if (name != other.name) return false
        if (fullName != other.fullName) return false
        if (description != other.description) return false
        if (updated != other.updated) return false
        if (stargazersCount != other.stargazersCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + updated.hashCode()
        result = 31 * result + stargazersCount
        return result
    }

}
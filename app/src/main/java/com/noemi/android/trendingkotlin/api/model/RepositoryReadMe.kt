package com.noemi.android.trendingkotlin.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepositoryReadMe(
    @SerializedName("content") val content: String?
) : Parcelable {

    override fun toString(): String {
        return "RepositoryReadMe: content=$content)"
    }
}
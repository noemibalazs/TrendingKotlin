package com.noemi.android.trendingkotlin.api.model

import com.google.gson.annotations.SerializedName

data class KotlinRepositories(
    @SerializedName("total_count") val total_count: Int = 0,
    @SerializedName("items") val items: MutableList<Repository>
) {

    override fun toString(): String {
        return "KotlinRepositories total_count: $total_count, items: ${items.size}"
    }
}
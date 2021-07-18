package com.noemi.android.trendingkotlin.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.noemi.android.trendingkotlin.TRENDING_TABLE

@Entity(tableName = TRENDING_TABLE)
data class RepositoryEntity(

    @PrimaryKey
    val id: Int,
    val name: String,
    val fullName: String,
    val description: String,
    val updated: String,
    val stargazers: Int
) {
    override fun toString(): String {
        return "RepositoryEntity:id=$id, name='$name', fullName='$fullName', description='$description', updated='$updated', watchers=$stargazers"
    }
}
package com.noemi.android.trendingkotlin.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRepositoryList(entityList: MutableList<RepositoryEntity>)

    @Query("SELECT * FROM trending_table WHERE stargazers >= :stars ORDER BY stargazers DESC")
    fun getFilteredRepositoryList(stars: Int): MutableList<RepositoryEntity>
}
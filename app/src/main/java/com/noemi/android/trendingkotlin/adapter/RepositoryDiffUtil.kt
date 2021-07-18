package com.noemi.android.trendingkotlin.adapter

import androidx.recyclerview.widget.DiffUtil
import com.noemi.android.trendingkotlin.api.model.Repository

class RepositoryDiffUtil : DiffUtil.ItemCallback<Repository>() {

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }
}
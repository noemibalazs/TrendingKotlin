package com.noemi.android.trendingkotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.noemi.android.trendingkotlin.R
import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.databinding.ItemRepositoryBinding

typealias RepositoryClickListener = (Repository) -> Unit

class RepositoryAdapter(private val clickListener: RepositoryClickListener) :
    ListAdapter<Repository, RepositoryVH>(RepositoryDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryVH {
        val binding: ItemRepositoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_repository,
            parent,
            false
        )
        return RepositoryVH(binding, clickListener)
    }

    override fun onBindViewHolder(holder: RepositoryVH, position: Int) {
        holder.bindRepository(getItem(position))
    }
}
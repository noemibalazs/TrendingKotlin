package com.noemi.android.trendingkotlin.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.noemi.android.trendingkotlin.R
import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.databinding.ItemRepositoryBinding
import com.noemi.android.trendingkotlin.util.OneTimeClickListener
import com.noemi.android.trendingkotlin.util.displayData

class RepositoryVH(
    private val binding: ItemRepositoryBinding,
    private val repositoryClickListener: RepositoryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bindRepository(repository: Repository) {
        binding.apply {
            tvRepoName.text = repository.name
            tvRepoDescription.text = repository.description

            tvUpdated.text = String.format(
                itemView.context.getString(R.string.txt_updated),
                repository.updated.displayData()
            )
            tvWatchers.text = String.format(
                itemView.context.getString(R.string.txt_watchers),
                repository.stargazersCount
            )

            cvRepositoryContainer.setOnClickListener(object : OneTimeClickListener() {
                override fun oneTimeClick(v: View) {
                    repositoryClickListener?.invoke(repository)
                }
            })
        }
    }
}
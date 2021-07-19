package com.noemi.android.trendingkotlin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.noemi.android.trendingkotlin.R
import com.noemi.android.trendingkotlin.adapter.*
import com.noemi.android.trendingkotlin.api.model.Repository
import com.noemi.android.trendingkotlin.app.App
import com.noemi.android.trendingkotlin.databinding.ActivityMainBinding
import com.noemi.android.trendingkotlin.details.RepositoryDetailsActivity
import com.noemi.android.trendingkotlin.mapper.Mapper
import com.noemi.android.trendingkotlin.preference.PreferenceRepository
import com.noemi.android.trendingkotlin.util.*
import com.noemi.android.trendingkotlin.viewModel.RepositoryViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RepositoryViewModel

    @Inject
    lateinit var mapper: Mapper

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    private val repositoryAdapter: RepositoryAdapter by lazy {
        RepositoryAdapter(clickListener)
    }

    private val clickListener: RepositoryClickListener = {
        preferenceRepository.repositoryID = it.id
        preferenceRepository.repositoryFullName = it.fullName
        launchActivity(RepositoryDetailsActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initBinding()
        viewModel.loadTrendingRepositories(mapper)
        initObservers()

        if (savedInstanceState != null) {
            val repos = savedInstanceState.getParcelableArrayList<Repository>(KEY_SAVED_INSTANCE)
            repositoryAdapter.submitList(repos)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val repositories = repositoryAdapter.currentList
        val repos = arrayListOf<Repository>()
        repos.addAll(repositories)
        outState.putParcelableArrayList(KEY_SAVED_INSTANCE, repos)
        super.onSaveInstanceState(outState)
    }

    private fun initBinding() {
        binding.rvRepositories.adapter = repositoryAdapter
        binding.viewModel = viewModel
    }

    private fun initObservers() {
        viewModel.trendingRepositories.observe(this, {
            repositoryAdapter.submitList(it)
        })

        viewModel.progressEvent.observe(this, {
            binding.progressBar.isVisible = it ?: false
        })

        viewModel.errorEvent.observe(this, {
            showToastToUser(it)
        })

        viewModel.searchClickEvent.observe(this, { search ->
            if (search) {
                onSearchedClicked(search)
            }
        })

        viewModel.filteredRepositories.observe(this, {
            populateUI(it)
        })

        viewModel.filterError.observe(this, { error ->
            if (error) {
                showToastToUser(getString(R.string.txt_try_it_again_error))
            }
        })

        viewModel.clearClickEvent.observe(this, { clear ->
            if (clear) {
                onClearClicked()
            }
        })
    }

    private fun onSearchedClicked(event: Boolean) {
        Log.d(TAG, "onSearchedClicked()")
        if (event) {
            if (lengthValid()) {
                viewModel.filterRepositories(getWatchers(), mapper)
                clearWatchers()
            } else {
                showToastToUser(getString(R.string.txt_search_should_not_be_empty))
            }
        }
    }

    private fun lengthValid() = etRepositoryStars.text.toString().trimEnd().isNotBlank()

    private fun getWatchers() = etRepositoryStars.text.toString().trimEnd().toInt()

    private fun clearWatchers() = etRepositoryStars.setText("")

    private fun populateUI(it: MutableList<Repository>) {
        repositoryAdapter.submitList(null)
        repositoryAdapter.submitList(it)
        binding.clClear.isVisible = true
    }

    private fun onClearClicked() {
        binding.clClear.isVisible = false
        repositoryAdapter.submitList(null)
        viewModel.loadTrendingRepositories(mapper)
    }

    companion object {

        private const val TAG = "MainActivity"
        private const val KEY_SAVED_INSTANCE = "key_saved_instance"
    }
}
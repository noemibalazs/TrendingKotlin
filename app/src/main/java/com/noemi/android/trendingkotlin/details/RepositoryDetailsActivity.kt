package com.noemi.android.trendingkotlin.details

import android.nfc.FormatException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.noemi.android.trendingkotlin.R
import com.noemi.android.trendingkotlin.api.model.RepositoryDetails
import com.noemi.android.trendingkotlin.api.model.RepositoryReadMe
import com.noemi.android.trendingkotlin.app.App
import com.noemi.android.trendingkotlin.databinding.ActivityRepositoryDetailsBinding
import com.noemi.android.trendingkotlin.preference.PreferenceRepository
import com.noemi.android.trendingkotlin.util.displayData
import com.noemi.android.trendingkotlin.util.showToastToUser
import com.noemi.android.trendingkotlin.viewModel.RepositoryViewModel
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class RepositoryDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: RepositoryViewModel

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    private lateinit var binding: ActivityRepositoryDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.instance.component.inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_details)

        viewModel.loadAllRepositoryDetails(
            preferenceRepository.repositoryID,
            preferenceRepository.repositoryFullName
        )

        initObserver()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val scrollPositions = arrayListOf<Int>(binding.nsDetails.scrollX, binding.nsDetails.scrollY)
        outState.putParcelable(KEY_REPOSITORY_DETAILS, viewModel.repositoryDetails.value)
        outState.putParcelable(KEY_REPOSITORY_READ_ME, viewModel.repositoryReadMe.value)
        outState.putIntegerArrayList(KEY_SCROLL_POSITIONS, scrollPositions)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        restoreUI(savedInstanceState)
    }

    private fun restoreUI(savedInstanceState: Bundle){
        val scrollPositions = savedInstanceState.getIntegerArrayList(KEY_SCROLL_POSITIONS)
        val readMe = savedInstanceState.getParcelable<RepositoryReadMe>(KEY_REPOSITORY_READ_ME)
        val repositoryDetails = savedInstanceState.getParcelable<RepositoryDetails>(KEY_REPOSITORY_DETAILS)

        readMe?.let { populateReadme(it) }
        repositoryDetails?.let { populateUI(it) }

        scrollPositions?.let {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.nsDetails.scrollTo(it[0], it[1])
            }, 900L)
        }
    }

    private fun initObserver() {
        viewModel.repositoryDetails.observe(this, {
            populateUI(it)
        })

        viewModel.repositoryReadMe.observe(this, {
            populateReadme(it)
        })

        viewModel.progressEvent.observe(this, {
            binding.pbDetails.isVisible = it ?: false
        })

        viewModel.errorEvent.observe(this, {
            showToastToUser(it)
        })
    }

    private fun populateUI(repositoryDetails: RepositoryDetails) {
        Log.d(TAG, "populateUI")
        binding.tvRepositoryName.text = repositoryDetails.name
        binding.tvRepositoryDescription.text =
            String.format(getString(R.string.txt_description), repositoryDetails.description)
        binding.tvRepositorySize.text =
            String.format(getString(R.string.txt_size), repositoryDetails.size)
        binding.tvRepositoryWatchers.text =
            String.format(getString(R.string.txt_watchers), repositoryDetails.stargazersCount)
        binding.tvRepositoryForks.text =
            String.format(getString(R.string.txt_forks), repositoryDetails.forksCount)
        binding.tvRepositoryIssues.text =
            String.format(getString(R.string.txt_open_issues), repositoryDetails.openIssuesCount)
        binding.tvRepositoryUpdated.text =
            String.format(getString(R.string.txt_updated), repositoryDetails.updated.displayData())
    }

    private fun populateReadme(readMe: RepositoryReadMe) {
        Log.d(TAG, "populateReadMe")
        readMe.content?.let {
            try {
                val decodedReadme: ByteArray = Base64.decode(it, Base64.NO_PADDING)
                val readMeText = String(decodedReadme, StandardCharsets.UTF_8)
                binding.tvReadmeContent.text = readMeText
                Log.d(TAG, "ReadMe: $readMeText")

            } catch (e: FormatException) {
                Log.e(TAG, "Exception thrown at decoding.")
            }
        }
    }

    companion object {
        private const val TAG = "RepositoryDetailsActivity"
        private const val KEY_SCROLL_POSITIONS = "key_scroll_positions"
        private const val KEY_REPOSITORY_READ_ME = "key_read_me"
        private const val KEY_REPOSITORY_DETAILS = "key_details"
    }
}
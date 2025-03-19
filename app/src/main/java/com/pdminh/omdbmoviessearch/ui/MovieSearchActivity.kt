package com.pdminh.omdbmoviessearch.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdminh.omdbmoviessearch.R
import com.pdminh.omdbmoviessearch.databinding.ActivityMovieSearchBinding
import com.pdminh.omdbmoviessearch.util.NetworkUtils
import com.pdminh.omdbmoviessearch.util.State
import com.pdminh.omdbmoviessearch.util.dismissKeyboard
import com.pdminh.omdbmoviessearch.util.getColorRes
import com.pdminh.omdbmoviessearch.util.hide
import com.pdminh.omdbmoviessearch.util.show
import com.pdminh.omdbmoviessearch.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieSearchBinding
    private val viewModel: MovieSearchViewModel by viewModels()
    @Inject
    lateinit var movieSearchAdapter: MovieSearchAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMovieSearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUI()
        initializeObserver()
        handleNetworkChanges()
        setupAPICall()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.apply {
            queryHint = getString(R.string.search)
            isSubmitButtonEnabled = true
            onActionViewExpanded()
        }
        search(searchView)
        return true
    }

    private fun setupUI() {
        binding.recyclerViewMovies.apply {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            adapter = movieSearchAdapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
                    layoutManager?.let {
                        val visibleItemCount = it.childCount
                        val totalItemCount = it.itemCount
                        val firstVisibleItemPosition = it.findFirstVisibleItemPosition()
                        viewModel.checkForLoadMoreItems(
                            visibleItemCount,
                            totalItemCount,
                            firstVisibleItemPosition
                        )
                    }
                }
            })
        }
    }

    private fun initializeObserver() {
        viewModel.queryLiveData.observe(this) { movieName ->
            Log.i("Info", "Movie Name = $movieName")
        }
        viewModel.loadMoreListLiveData.observe(this) { loadMore ->
            if (loadMore) {
                movieSearchAdapter.setData(null)
                Handler().postDelayed({
                    viewModel.loadMore()
                }, 2000)
            }
        }
    }

    private fun handleNetworkChanges() {
        NetworkUtils.getNetworkLiveData(applicationContext).observe(this) { isConnected ->
            if (!isConnected) {
                binding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.apply {
                    show()
                    setBackgroundColor(getColorRes(R.color.colorStatusNotConnected))
                }
            } else {
                if (viewModel.moviesLiveData.value is State.Error || movieSearchAdapter.itemCount == 0) {
                    viewModel.getMovies()
                }
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.apply {
                    setBackgroundColor(getColorRes(R.color.colorStatusConnected))

                    animate()
                        .alpha(1f)
                        .setStartDelay(ANIMATION_DURATION)
                        .setDuration(ANIMATION_DURATION)
                        .setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator) {
                                hide()
                            }
                        })
                }
            }
        }
    }

    private fun setupAPICall() {
        viewModel.moviesLiveData.observe(this) { state ->
            when (state) {
                is State.Loading -> {
                    binding.recyclerViewMovies.hide()
                    binding.linearLayoutSearch.hide()
                    binding.progressBar.show()
                }

                is State.Success -> {
                    binding.recyclerViewMovies.show()
                    binding.linearLayoutSearch.hide()
                    binding.progressBar.hide()
                    movieSearchAdapter.setData(state.data)
                }

                is State.Error -> {
                    binding.progressBar.hide()
                    showToast(state.message)
                }
            }
        }
    }

    private fun search(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                dismissKeyboard(searchView)
                searchView.clearFocus()
                viewModel.searchMovie(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    companion object {
        const val ANIMATION_DURATION = 1000.toLong()
    }
}
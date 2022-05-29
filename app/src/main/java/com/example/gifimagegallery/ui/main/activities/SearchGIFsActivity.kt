package com.example.gifimagegallery.ui.main.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gifimagegallery.R
import com.example.gifimagegallery.adapters.GifsAdapter
import com.example.gifimagegallery.adapters.GifsLoaderStateAdapter
import com.example.gifimagegallery.databinding.ActivitySearchGifsBinding
import com.example.gifimagegallery.enums.RemotePresentationState
import com.example.gifimagegallery.extensions.asRemotePresentationState
import com.example.gifimagegallery.ui.main.viewModels.SearchGIFsViewModel
import com.example.gifimagegallery.ui.main.viewModels.UiAction
import com.example.gifimagegallery.ui.main.viewModels.UiModel
import com.example.gifimagegallery.ui.main.viewModels.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchGIFsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchGifsBinding
    private val viewModel by lazy { ViewModelProvider(this).get(SearchGIFsViewModel::class.java) }
    private var adapter: GifsAdapter? = null
    private var header: GifsLoaderStateAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_gifs)
        binding.lifecycleOwner = this

        //
        initAdapter()
        bindState(
            uiState = viewModel.state,
            pagingData = viewModel.pagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun bindState(
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>,
        uiActions: (UiAction) -> Unit
    ) {
        bindSearch(
            uiState = uiState,
            onQueryChanged = uiActions
        )

        adapter?.let {
            bindList(
                gifsListAdapter = it,
                uiState = uiState,
                pagingData = pagingData,
                onScrollChanged = uiActions
            )
        }
    }

    private fun bindList(
        gifsListAdapter: GifsAdapter,
        uiState: StateFlow<UiState>,
        pagingData: Flow<PagingData<UiModel>>,
        onScrollChanged: (UiAction) -> Unit
    ) {
        binding.retryButton.setOnClickListener { adapter?.retry() }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(UiAction.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = gifsListAdapter.loadStateFlow
            .asRemotePresentationState()
            .map { it == RemotePresentationState.PRESENTED }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest {
                adapter?.submitData(it)
            }
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) binding.recyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter?.loadStateFlow?.collect { loadState ->
                header?.loadState =
                    loadState.mediator
                        ?.refresh
                        ?.takeIf { it is LoadState.Error && adapter?.let { adapter -> adapter.itemCount > 0 } ?: false }
                        ?: loadState.prepend
                val isListEmpty =
                    loadState.refresh is LoadState.NotLoading && adapter?.itemCount == 0

                binding.emptyListTextView.isVisible = isListEmpty

                binding.recyclerView.isVisible =
                    loadState.source.refresh is LoadState.NotLoading || loadState.mediator?.refresh is LoadState.NotLoading

                binding.progressBar.isVisible = loadState.mediator?.refresh is LoadState.Loading

                binding.retryButton.isVisible =
                    loadState.mediator?.refresh is LoadState.Error && adapter?.itemCount == 0

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        this@SearchGIFsActivity,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }
    }

    private fun bindSearch(uiState: StateFlow<UiState>, onQueryChanged: (UiAction.Search) -> Unit) {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateGifsListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }
        binding.searchEditText.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateGifsListFromInput(onQueryChanged)
                true
            } else {
                false
            }
        }

        lifecycleScope.launch {
            uiState.map { it.query }
                .distinctUntilChanged()
                .collect(binding.searchEditText::setText)
        }
    }

    private fun initAdapter() {
        adapter = GifsAdapter()
        header = GifsLoaderStateAdapter { adapter?.retry() }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter?.withLoadStateHeaderAndFooter(
            header = header ?: GifsLoaderStateAdapter { adapter?.retry() },
            footer = GifsLoaderStateAdapter { adapter?.retry() }

        )
    }

    private fun updateGifsListFromInput(onQueryChanged: (UiAction.Search) -> Unit) {
        binding.searchEditText.text.trim().let {
            if (it.isNotEmpty()) {
                binding.recyclerView.scrollToPosition(0)
                onQueryChanged(UiAction.Search(query = it.toString()))
            }
        }
    }
}
package com.example.gifimagegallery.extensions

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.gifimagegallery.enums.RemotePresentationState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.scan

@OptIn(ExperimentalCoroutinesApi::class)
fun Flow<CombinedLoadStates>.asRemotePresentationState(): Flow<RemotePresentationState> =
    scan(RemotePresentationState.INITIAL) { state, loadState ->
        when (state) {
            RemotePresentationState.PRESENTED -> when (loadState.mediator?.refresh) {
                is LoadState.Loading -> RemotePresentationState.REMOTE_LOADING
                else -> state
            }
            RemotePresentationState.INITIAL -> when (loadState.mediator?.refresh) {
                is LoadState.Loading -> RemotePresentationState.REMOTE_LOADING
                else -> state
            }
            RemotePresentationState.REMOTE_LOADING -> when (loadState.mediator?.refresh) {
                is LoadState.Loading -> RemotePresentationState.SOURCE_LOADING
                else -> state
            }
            RemotePresentationState.SOURCE_LOADING -> when (loadState.mediator?.refresh) {
                is LoadState.NotLoading -> RemotePresentationState.PRESENTED
                else -> state
            }
        }
    }
        .distinctUntilChanged()

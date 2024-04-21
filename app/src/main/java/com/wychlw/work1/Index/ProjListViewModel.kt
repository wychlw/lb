package com.wychlw.work1.Index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProjListViewModel(projRepository: ProjRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val indexProjList: StateFlow<indexProjAllState> =
        projRepository.getAllProj().map {
            indexProjAllState(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = indexProjAllState()
        )
}

data class indexProjAllState(
    val projList: List<ProjDb> = listOf()
)
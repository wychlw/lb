package com.wychlw.work1.Index

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjItemDb
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

data class IndexUiState(
    val context: Context?,
    var projList: MutableState<List<ProjDb>>,
    var currentProj: MutableState<ProjDb>,
    var currentColList: MutableState<List<ProjColDb>>,
    var currentCol: MutableState<ProjColDb>,
    var currentItemList: MutableState<List<ProjItemDb>>
) {
    companion object {
        private var instance: MutableState<IndexUiState>? = null
        @Composable
        fun getInstance(): MutableState<IndexUiState> {
            if (instance == null) {
                val ctx = LocalContext.current
                instance = initIndexUiState(context = ctx)
            }
            return instance as MutableState<IndexUiState>
        }

        @Composable
        fun ReInit() {
            val ctx = LocalContext.current
            instance = initIndexUiState(context = ctx)
        }
    }
}
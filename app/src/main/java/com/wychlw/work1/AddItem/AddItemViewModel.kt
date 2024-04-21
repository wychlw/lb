package com.wychlw.work1.AddItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wychlw.work1.CurrentView
import com.wychlw.work1.Index.IndexUiState

data class AddItemUiState(
    val projState: IndexUiState,
    val title: MutableState<String>,
    val description: MutableState<String>,
    val status: MutableState<Int>
)

@Composable
fun initAddItemUiState(): MutableState<AddItemUiState> {
    val indexState = IndexUiState.getInstance().value
    val addItemState = remember {
        mutableStateOf(AddItemUiState(
            projState = indexState,
            title = mutableStateOf(""),
            description = mutableStateOf(""),
            status = mutableStateOf(0)
        ))
    }
    return addItemState
}
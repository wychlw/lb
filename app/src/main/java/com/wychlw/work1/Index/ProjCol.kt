package com.wychlw.work1.Index

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProjCol(modifier: Modifier = Modifier, state: MutableState<IndexUiState>) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.value.currentItemList.value) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                ProjCard(modifier = modifier, item = it)
            }
        }
//        for (i in state.value.currentItemList.value) {
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp)
//            ) {
//                ProjCard(modifier = modifier, item = i)
//            }
//        }
    }
}
package com.wychlw.work1.Index

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wychlw.work1.CurrentView
import com.wychlw.work1.data.ProjDatabase
import kotlinx.coroutines.launch

@Composable
fun ColumnSelector(
    modifier: Modifier = Modifier,
    state: MutableState<IndexUiState>,
    expendColumnSelector: MutableState<Boolean>
) {
    val dao = ProjDatabase.getInstance(LocalContext.current).projDao()
    val scope = rememberCoroutineScope()
    DropdownMenu(
        modifier = modifier
            .fillMaxWidth(),
        expanded = expendColumnSelector.value,
        onDismissRequest = { expendColumnSelector.value = false },
    ) {
        for (i in state.value.currentColList.value) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = i.name,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                onClick = {
                    state.value.currentCol.value = i
                    scope.launch {
                        dao.getProjItem(i.cid).collect() {
                            state.value.currentItemList.value = it
                        }
                    }
                    expendColumnSelector.value = false
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onSurface

                )
            )
        }

    }
}

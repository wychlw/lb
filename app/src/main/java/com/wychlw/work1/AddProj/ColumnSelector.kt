package com.wychlw.work1.AddProj

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
    state: MutableState<AddProjUiState>,
    expendColumnSelector: MutableState<Boolean>
) {
    DropdownMenu(
        modifier = modifier
            .fillMaxWidth(),
        expanded = expendColumnSelector.value,
        onDismissRequest = { expendColumnSelector.value = false },
    ) {
        for (i in ProjType.values()) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = i,
                        modifier = Modifier.padding(8.dp)
                    )
                },
                onClick = {
                    state.value.projType.value = i
                    expendColumnSelector.value = false
                    if (i == ProjType.LOCAL) {
                        state.value.projData.value.local = ProjLocal()
                    } else if (i == ProjType.REMOTE){
                        state.value.projData.value.remote = ProjRemote()
                    }
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }

    }
}

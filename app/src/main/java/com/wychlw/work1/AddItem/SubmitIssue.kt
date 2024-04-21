package com.wychlw.work1.AddItem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wychlw.work1.AppState
import com.wychlw.work1.CurrentView
import com.wychlw.work1.Index.IndexUiState
import com.wychlw.work1.Index.fillIndexUiState
import com.wychlw.work1.Index.initIndexUiState
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.data.ProjItemTimelineDb
import com.wychlw.work1.functional.getStatIcon
import com.wychlw.work1.functional.getStatText
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun SubmitIssue(modifier: Modifier = Modifier, state: MutableState<AddItemUiState>) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()
    val indexState = IndexUiState.getInstance()
    val appState = AppState.getInstance()
    val stateSelectStat = remember{ mutableStateOf(false) }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        ElevatedButton(onClick = {
            stateSelectStat.value = true
        }) {
            Text(
                text = "Statues?"
            )
        }
        DropdownMenu(
            expanded = stateSelectStat.value,
            onDismissRequest = { stateSelectStat.value = !stateSelectStat.value }
        ) {
            for (i in 0..3) {
                DropdownMenuItem(
                    text = {
                        Row {
                            val iconI = getStatIcon(i)
                            val iconIm = iconI.first
                            val iconCo = iconI.second
                            Icon(
                                imageVector = iconIm,
                                contentDescription = "Status",
                                tint = iconCo,
                            )
                            val txt = getStatText(i)
                            Text(
                                text = txt,
                                modifier = modifier.padding(start = 6.dp)
                            )
                        }
                    },
                    onClick = {
                        stateSelectStat.value = !stateSelectStat.value
                        state.value.status.value = i
                    }
                )
            }
        }
        Spacer(modifier = modifier.weight(1f))
        FilledTonalButton(onClick = {
            val dao = ProjDatabase.getInstance(ctx).projDao()
            val projItemDb = ProjItemDb(
                0,
                state.value.projState.currentCol.value.cid,
                0, // unused for now
                state.value.title.value,
                state.value.status.value,
                ""
            )
            scope.launch {
                dao.insertProjItem(projItemDb)
                dao.getProjItem(projItemDb.cid).collect() {
                    var iid = 0
                    it.forEach {
                        if (it.title == projItemDb.title) {
                            iid = it.id
                        }
                    }
                    val projItemTimelineDb = ProjItemTimelineDb(
                        0,
                        iid,
                        "admin",
                        0,
                        LocalDateTime.now().toString(),
                        state.value.description.value
                    )
                    indexState.value.currentItemList.value = it
                    dao.insertProjItemTimeline(projItemTimelineDb)
                    appState.value.navController.popBackStack()
                }
            }
        }) {
            val iconI = getStatIcon(state.value.status.value)
            val iconIm = iconI.first
            val iconCo = iconI.second
            Icon(
                imageVector = iconIm,
                contentDescription = "Status",
                tint = iconCo,
            )
            Text(
                modifier = modifier.padding(start = 6.dp),
                text = "Open as "
            )
            Text(
                text = getStatText(state.value.status.value),
                color = iconCo
            )
        }
    }
}
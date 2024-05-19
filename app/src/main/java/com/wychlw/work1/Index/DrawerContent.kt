package com.wychlw.work1.Index

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wychlw.work1.AppState
import com.wychlw.work1.CurrentView
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDatabase
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(modifier: Modifier = Modifier, state: MutableState<IndexUiState>, drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    Card (
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(0.8f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column (
            modifier = modifier
                .padding(16.dp)
        ) {
            Text(
                text = "My Projects",
                modifier = modifier
                    .padding(top = 4.dp, bottom = 8.dp)
            )
            HorizontalDivider()
            state.value.projList.value.forEach() {pit ->
                NavigationDrawerItem(
                    modifier = modifier
                        .padding(8.dp),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        unselectedTextColor = MaterialTheme.colorScheme.onSurface,
                        unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                        unselectedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    label = { Text(text = pit.name) },
                    selected = state.value.currentProj.value.pid == pit.pid,
                    onClick = {
                        if (state.value.currentProj.value.pid == pit.pid) {
                            scope.launch { drawerState.close() }
                            return@NavigationDrawerItem
                        }
                        state.value.currentProj.value = pit
                        scope.launch {
                            launch { drawerState.close() }
                            val dao = ProjDatabase.getInstance(ctx).projDao()
                            launch {
                                dao.getProjCol(pit.pid).collect() {
                                    state.value.currentColList.value = it
                                    state.value.currentCol.value = if (it.isNotEmpty()) it[0] else ProjColDb(0, pit.pid, "")
                                    if (it.isNotEmpty()) {
                                        dao.getProjItem(it[0].cid).collect() {
                                            state.value.currentItemList.value = it
                                        }
                                    }
                                }
                            }
                        }
                    }
                )
            }
            Spacer(modifier.weight(1f))
            FilledTonalIconButton(
                onClick = {
                    val appState = AppState.getInstance()
                    appState.value.navController.navigate(CurrentView.AddProj)
                },
                modifier = modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add"
                )
            }
        }
    }
}
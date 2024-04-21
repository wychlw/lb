package com.wychlw.work1.Index

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexView(
    modifier: Modifier = Modifier,
    state: MutableState<IndexUiState>
) {
    val expendColumnSelector = remember { mutableStateOf(false) }
    val drawerState = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            PrimaryTopBar(projName = state.value.currentProj.value.name, drawerState = drawerState)
        },
        floatingActionButton = {
            FloatAddButton()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(18.dp),
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            ) {
                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = "${state.value.currentCol.value.name} (${state.value.currentItemList.value.size})",
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp),
                        )
                        Spacer(modifier.weight(1f))
                        IconButton(
                            onClick = {
                                expendColumnSelector.value = !expendColumnSelector.value
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "More"
                            )
                        }
                    }
                }
                ColumnSelector(
                    modifier = modifier,
                    state = state,
                    expendColumnSelector = expendColumnSelector
                )
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                ProjCol(modifier = modifier, state = state)
            }
        }
    }
}
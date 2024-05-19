package com.wychlw.work1.AddProj

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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

@Composable
fun AddProjView(modifier: Modifier = Modifier, state: MutableState<AddProjUiState>) {
    val expendColumnSelector = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            PrimaryTopBar()
        },
        modifier = modifier.fillMaxWidth()
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Column {
                Row {
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
                                text = "Current type: ${state.value.projType.value}",
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
                HorizontalDivider()
                if (state.value.projType.value == ProjType.LOCAL) {
                    LocalProjView(modifier = modifier, state = state)
                } else {
                    RemoteProjView(modifier = modifier, state = state)
                }
                HorizontalDivider()
                SubmitAdd(modifier = modifier, state = state)
            }
        }
    }
}
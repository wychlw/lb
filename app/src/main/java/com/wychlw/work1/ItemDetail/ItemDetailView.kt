package com.wychlw.work1.ItemDetail

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.data.ProjItemTimelineDb
import java.time.LocalDateTime
import java.time.Period

data class ItemDetailUiState(
    val item: MutableState<ProjItemDb>,
    val timeline: MutableState<List<ProjItemTimelineDb>>
)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun initItemDetailUiState(item: ProjItemDb): MutableState<ItemDetailUiState> {
    val state = remember {
        mutableStateOf(
            ItemDetailUiState(
                item = mutableStateOf(item),
                timeline = mutableStateOf(
                    listOf(
                        ProjItemTimelineDb(
                            0,
                            0,
                            "admin",
                            0,
                            "2024-01-01T00:00:00.000000000",
                            "Hello, world!"
                        )
                    )
                )
            )
        )
    }
    return state
}

@Composable
fun ItemDetailView(modifier: Modifier = Modifier, state: MutableState<ItemDetailUiState>) {
    Scaffold(
        topBar = {
            PrimaryTopBar(state = state)
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            Column {
                OutlinedCard(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = modifier
                            .padding(8.dp),
                    ) {
                        Row {
                            Text(
                                text = state.value.timeline.value[0].by,
                                style = MaterialTheme.typography.titleLarge
                            )
                            val tdiff = Period.between(
                                LocalDateTime.parse(state.value.timeline.value[0].time)
                                    .toLocalDate(),
                                LocalDateTime.now().toLocalDate()
                            )
                            Text(
                                text = "Modified ${tdiff.days} day ago",
                                style = MaterialTheme.typography.titleSmall,
                                modifier = modifier
                                    .align(Alignment.Bottom)
                                    .padding(start = 8.dp),
                                color = Color(0xAA777777)
                            )
                        }
                        Row {
                            HorizontalDivider(
                                modifier = modifier
                                    .padding(top = 8.dp, bottom = 8.dp)
                                    .fillMaxWidth(),
                                thickness = 1.5.dp,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                        Row {
                            Text(
                                text = state.value.timeline.value[0].content,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
                LazyColumn(
                    modifier = modifier.fillMaxWidth()
                ) {
                    items(state.value.timeline.value) {it ->
                        when (it.action) {
                            0 -> {
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                        .padding(16.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Circle,
                                        contentDescription = "TodoItem",
                                        modifier = modifier
                                            .padding(end = 16.dp)
                                    )
                                    Text(
                                        text = it.by,
                                    )
                                    val tdiff = Period.between(
                                        LocalDateTime.parse(it.time)
                                            .toLocalDate(),
                                        LocalDateTime.now().toLocalDate()
                                    )
                                    Text(
                                        text = "Modified ${tdiff.days} day ago",
                                        style = MaterialTheme.typography.titleSmall,
                                        modifier = modifier
                                            .align(Alignment.Bottom)
                                            .padding(start = 8.dp),
                                        color = Color(0xAA777777)
                                    )
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
package com.wychlw.work1

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wychlw.work1.Index.ProjColumn
import com.wychlw.work1.Index.ProjItem
import com.wychlw.work1.Index.ProjItemStatus
import com.wychlw.work1.Index.ProjListViewModel
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.data.tryInit
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class IndexActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndexActivityCreate(context = applicationContext)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun InitIndexUiState(state: MutableState<IndexUiState>) {
    val scope = rememberCoroutineScope()
    scope.launch {
        tryInit(state.value.context!!)
        val dao = ProjDatabase.getInstance(state.value.context!!).projDao()
        dao.getAllProj().collect() {
            println("Begin Collect")
            for (i in it) {
                println(i.name)
            }
//            state.value.projList.value = it
//            state.value.currentProj.value = it[0]
        }
    }
}

@Composable
fun IndexActivityCreate(modifier: Modifier = Modifier, context: Context) {
    val exampleProj = remember {
        mutableStateOf(ProjDb(1, "Local", 1))
    }
    val exampleProjList = remember {
        mutableStateOf(listOf(exampleProj.value))
    }
    val exampleColDraft = remember {
        mutableStateOf(ProjColDb(1, 1, "Draft"))
    }
    val exampleColInprog = remember {
        mutableStateOf(ProjColDb(2, 1, "In Progress"))
    }
    val exampleColFin = remember {
        mutableStateOf(ProjColDb(3, 1, "Finish"))
    }
    val exampleColList = remember {
        mutableStateOf(listOf(exampleColDraft.value, exampleColInprog.value, exampleColFin.value))
    }
    val exampleItem1 = remember {
        mutableStateOf(ProjItemDb(1, 1, 1, "Exp1", 0, ""))
    }
    val exampleItemList = remember {
        mutableStateOf(listOf(exampleItem1.value))
    }

    val state = remember {
        mutableStateOf(
            IndexUiState(
                context,
                exampleProjList,
                exampleProj,
                exampleColList,
                exampleColDraft,
                exampleItemList
            )
        )
    }

    InitIndexUiState(state)

    AppTheme {
        IndexView(state = state)
    }
}

data class IndexUiState(
    val context: Context?,
    var projList: MutableState<List<ProjDb>>,
    var currentProj: MutableState<ProjDb>,
    var currentColList: MutableState<List<ProjColDb>>,
    var currentCol: MutableState<ProjColDb>,
    var currentItemList: MutableState<List<ProjItemDb>>
)

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

@Composable
fun ColumnSelector(
    modifier: Modifier = Modifier,
    state: MutableState<IndexUiState>,
    expendColumnSelector: MutableState<Boolean>
) {
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
                    println("Last: ${state.value.currentCol.value.name}")
                    state.value.currentCol.value = i
                    println("Current: ${state.value.currentCol.value.name}")
                    expendColumnSelector.value = false
                },
                colors = MenuDefaults.itemColors(
                    textColor = MaterialTheme.colorScheme.onSurface

                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopBar(
    modifier: Modifier = Modifier,
    projName: String,
    drawerState: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                text = projName,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    drawerState.value = true
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "More",
                )
            }
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatAddButton() {
    FloatingActionButton(
        onClick = {},
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add Proj"
        )
    }
}

@Composable
fun ProjCol(modifier: Modifier = Modifier, state: MutableState<IndexUiState>) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        for (i in state.value.currentItemList.value) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                ProjCard(modifier = modifier, item = i)
            }
        }
    }
}

@Composable
fun ProjCard(modifier: Modifier = Modifier, item: ProjItemDb) {
    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row {
            val iconIm = when (item.status) {
                0 -> Icons.Outlined.AddCircle
                1 -> Icons.Outlined.Info
                2 -> Icons.Outlined.CheckCircle
                else -> Icons.Outlined.AddCircle
            }
            val iconCo = when (item.status) {
                0 -> Color.Gray
                1 -> Color(0xFF2E8B57)
                2 -> Color(0xFFDA70D6)
                else -> Color.Gray
            }
            Icon(
                imageVector = iconIm,
                contentDescription = "Status",
                tint = iconCo,
                modifier = modifier
                    .padding(8.dp)
            )
            Text(
                text = "#${item.id}",
                modifier = modifier
                    .padding(8.dp),
                color = Color(0xCC444444)
            )
            Spacer(modifier = modifier.weight(1f))
            val assign = if (item.assign == "") "Unassigned" else item.assign
            Text(
                text = assign,
                modifier = modifier
                    .padding(8.dp)
                    .padding(end = 16.dp)
                    .weight(1f),
            )
        }
        Row {
            Text(
                text = item.title,
                modifier = modifier
                    .padding(8.dp)
                    .weight(1f),
                fontSize = 20.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndexActivityPreview() {
    val exampleProj = remember {
        mutableStateOf(ProjDb(1, "Local", 1))
    }
    val exampleProjList = remember {
        mutableStateOf(listOf(exampleProj.value))
    }
    val exampleColDraft = remember {
        mutableStateOf(ProjColDb(1, 1, "Draft"))
    }
    val exampleColInprog = remember {
        mutableStateOf(ProjColDb(2, 1, "In Progress"))
    }
    val exampleColFin = remember {
        mutableStateOf(ProjColDb(3, 1, "Finish"))
    }
    val exampleColList = remember {
        mutableStateOf(listOf(exampleColDraft.value, exampleColInprog.value, exampleColFin.value))
    }
    val exampleItem1 = remember {
        mutableStateOf(ProjItemDb(1, 1, 1, "Exp1", 0, ""))
    }
    val exampleItemList = remember {
        mutableStateOf(listOf(exampleItem1.value))
    }

    val state = remember {
        mutableStateOf(
            IndexUiState(
                null,
                exampleProjList,
                exampleProj,
                exampleColList,
                exampleColDraft,
                exampleItemList
            )
        )
    }

    AppTheme {
        IndexView(modifier = Modifier, state = state)
    }
}
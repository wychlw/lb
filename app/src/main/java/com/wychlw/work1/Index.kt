package com.wychlw.work1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.sharp.AddCircle
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class Index : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var test_item_1 = ProjItem(1, "Test")
        test_item_1.status = ProjItemStatus.Open
        var test_col1 = ProjColumn("Backlog")
        test_col1.items = listOf(test_item_1)
        var test_item_2 = ProjItem(2, "Test2")
        test_item_2.status = ProjItemStatus.Closed
        var test_col2 = ProjColumn("Done")
        test_col2.items = listOf(test_item_2, test_item_1)

        var test_proj = Proj(1, "Test")
        test_proj.columns = listOf(test_col1, test_col2)
        var test_proj_lst = listOf(test_proj)
        setContent {
            AppTheme {
                IndexView(projLst = test_proj_lst)
            }
        }
    }
}

enum class ProjItemAction {
    Open, Add, Move, Convert, Assign, Commit, Close
}

enum class ProjItemStatus {
    Draft, Open, Closed
}

class ProjItemTimeline(var by: String = "", var action: ProjItemAction = ProjItemAction.Open, var time: LocalDateTime = LocalDateTime.now(), var content: String = ""){
}

class ProjItem(var id: Int = 0, var title: String = "") {
    var timeline: List<ProjItemTimeline> = listOf()
    var status: ProjItemStatus = ProjItemStatus.Draft
    var assign: String = ""
}

class ProjColumn(var name: String = "") {
    var items: List<ProjItem> = listOf()
}

class Proj(var id: Int = 0, var name: String = "") {
    var columns: List<ProjColumn> = listOf()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexView(modifier: Modifier = Modifier, projLst: List<Proj>) {
    val expendColumnSelector = remember { mutableStateOf(false) }
    val currentColumn = remember { mutableStateOf(0) }
    val drawerState = remember { mutableStateOf(false)}
    val currentProj = remember { mutableStateOf(0)}
    val proj = projLst[currentProj.value]
    Scaffold(
        topBar = {
            PrimaryTopBar(projName = proj.name, drawerState = drawerState)
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
            Box (
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
                            text = "${proj.columns[currentColumn.value].name} (${proj.columns[currentColumn.value].items.size})",
                            modifier = modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 16.dp),
                        )
                        Spacer(modifier.weight(1f))
                        IconButton(
                            onClick = { expendColumnSelector.value = !expendColumnSelector.value },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ArrowDropDown,
                                contentDescription = "More"
                            )
                        }
                    }
                }
                DropdownMenu(
                    modifier = modifier
                        .fillMaxWidth(),
                    expanded = expendColumnSelector.value,
                    onDismissRequest = { expendColumnSelector.value = false },
                ) {
                    for (i in proj.columns) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = i.name,
                                    modifier = Modifier.padding(8.dp)
                                )
                            },
                            onClick = {
                                currentColumn.value = proj.columns.indexOf(i)
                                expendColumnSelector.value = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = MaterialTheme.colorScheme.onSurface

                            )
                        )
                    }

                }
            }
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                ProjCol(modifier = modifier, item = proj.columns[currentColumn.value])
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopBar(modifier: Modifier = Modifier, projName: String, drawerState: MutableState<Boolean>) {
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
fun ProjCol(modifier: Modifier = Modifier, item: ProjColumn) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        for (i in item.items) {
            Row (
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
fun ProjCard(modifier: Modifier = Modifier, item: ProjItem) {
    OutlinedCard (
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
                ProjItemStatus.Draft -> Icons.Outlined.AddCircle
                ProjItemStatus.Open -> Icons.Outlined.Info
                ProjItemStatus.Closed -> Icons.Outlined.CheckCircle
            }
            val iconCo = when (item.status) {
                ProjItemStatus.Draft -> Color.Gray
                ProjItemStatus.Open -> Color(0xFF2E8B57)
                ProjItemStatus.Closed -> Color(0xFFDA70D6)
            }
            Icon (
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
            Text (
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
fun IndexPreview() {
    var test_item_1 = ProjItem(1, "Test")
    test_item_1.status = ProjItemStatus.Open
    var test_col1 = ProjColumn("Backlog")
    test_col1.items = listOf(test_item_1)
    var test_item_2 = ProjItem(2, "Test2")
    test_item_2.status = ProjItemStatus.Closed
    var test_col2 = ProjColumn("Done")
    test_col2.items = listOf(test_item_2, test_item_1)

    var test_proj = Proj(1, "TestLocal")
    test_proj.columns = listOf(test_col1, test_col2)
    var test_proj_lst = listOf(test_proj)
    AppTheme {
        IndexView(modifier = Modifier, projLst = test_proj_lst)
    }
}
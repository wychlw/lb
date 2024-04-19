package com.wychlw.work1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class index : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                IndexView()
            }
        }
    }
}

class Column {
    var column_now: String
    var column_list: List<String>
    init {
        column_list = listOf("Todo", "Doing", "Done")
        column_now = column_list[0]
    }
}

class Location {
    var location_now: String
    var location_list: List<String>
    var location_column: Map<String, Column>
    init {
        location_list = listOf("Local")
        location_now = location_list[0]
        location_column = mapOf("Local" to Column())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexView(modifier: Modifier = Modifier, location: Location = Location()) {
    val count: MutableState<Int> = remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            PrimaryTopBar(location = location)
        },
        floatingActionButton = {
            FloatAddButton(count = count)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text =
                """
                    This is an example of a scaffold. It uses the Scaffold composable's parameters to create a screen with a simple top app bar, bottom app bar, and floating action button.

                    It also contains some basic inner content, such as this text.

                    You have pressed the floating action button ${count.value} times.
                """.trimIndent(),
            )
            Text("Count: ${count.value}", modifier = Modifier.padding(16.dp))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTopBar(modifier: Modifier = Modifier, location: Location) {
    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        title = {
            Text(
                text = location.location_now,
                maxLines = 1
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /*TODO*/ },
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
fun FloatAddButton(count: MutableState<Int>) {
    FloatingActionButton(
        onClick = { count.value++ },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add Proj"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun IndexPreview() {
    AppTheme {
        IndexView()
    }
}
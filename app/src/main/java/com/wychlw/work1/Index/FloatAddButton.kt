package com.wychlw.work1.Index

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.wychlw.work1.AppState
import com.wychlw.work1.CurrentView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FloatAddButton() {
    val appState = AppState.getInstance()

    FloatingActionButton(
        onClick = {
            appState.value.navController.navigate(CurrentView.AddItem)
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = "Add Proj"
        )
    }
}
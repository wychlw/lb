package com.wychlw.work1.AddProj

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun LocalProjView(modifier: Modifier = Modifier, state: MutableState<AddProjUiState>) {
    // input project name
    Row {
        OutlinedTextField(
            value = state.value.projData.value.local!!.name.value,
            onValueChange = {
                state.value.projData.value.local!!.name.value = it
            },
            label = { Text("Project name") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
}
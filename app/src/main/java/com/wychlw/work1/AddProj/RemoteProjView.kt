package com.wychlw.work1.AddProj

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@Composable
fun RemoteProjView(modifier: Modifier = Modifier, state: MutableState<AddProjUiState>) {
    Row {
        OutlinedTextField(
            value = state.value.projData.value.remote!!.user.value,
            onValueChange = {
                state.value.projData.value.remote!!.user.value = it
            },
            label = { Text("User name") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
    Row {
        OutlinedTextField(
            value = state.value.projData.value.remote!!.remote_id.value.toString(),
            onValueChange = {
                // is Int?
                if (it.toIntOrNull() != null) {
                    state.value.projData.value.remote!!.remote_id.value = it.toInt()
                }
            },
            label = { Text("Project id") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
    Row {
        OutlinedTextField(
            value = state.value.projData.value.remote!!.token.value,
            onValueChange = {
                state.value.projData.value.remote!!.token.value = it
            },
            label = { Text("Access token") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
}
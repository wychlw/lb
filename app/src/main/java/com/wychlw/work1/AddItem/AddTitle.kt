package com.wychlw.work1.AddItem

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AddTitle(modifier: Modifier = Modifier, state: MutableState<AddItemUiState>) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Add a title", fontSize = 24.sp, textAlign = TextAlign.Center
        )
    }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.value.title.value,
            onValueChange = { it: String ->
                state.value.title.value = it
            },
            label = { Text("Title") },
            singleLine = true,
            modifier = modifier.fillMaxWidth()
        )
    }
}
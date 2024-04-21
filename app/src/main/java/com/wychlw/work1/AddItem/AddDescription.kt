package com.wychlw.work1.AddItem

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AddDescription(modifier: Modifier = Modifier, state: MutableState<AddItemUiState>) {
    val ctx = LocalContext.current
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Add a description", fontSize = 18.sp, textAlign = TextAlign.Center
        )
    }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = state.value.description.value,
            onValueChange = { it: String ->
                state.value.description.value = it
            },
            label = { Text("Add your description here...") },
            minLines = 14,
            maxLines = 14,
            modifier = modifier.fillMaxWidth()
        )
    }
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        TextButton(
            onClick = {
                val url =
                    "https://docs.github.com/github/writing-on-github/getting-started-with-writing-and-formatting-on-github/basic-writing-and-formatting-syntax"
                val urlIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
                ctx.startActivity(urlIntent)
            }
        ) {
            Text(
                text = "Markdown syntax",
                fontSize = 10.sp
            )
        }
    }
}
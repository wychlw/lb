package com.wychlw.work1.AddItem

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wychlw.work1.CurrentView
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.data.ProjItemTimelineDb
import com.wychlw.work1.functional.getStatIcon
import com.wychlw.work1.functional.getStatText
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun AddItemView(modifier: Modifier = Modifier, state: MutableState<AddItemUiState>) {

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
                AddTitle(state = state, modifier = modifier)
                HorizontalDivider(
                    modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                AddDescription(state = state, modifier = modifier)
                HorizontalDivider(
                    modifier = modifier.padding(top = 8.dp, bottom = 8.dp)
                )
                SubmitIssue(state = state, modifier = modifier)
            }
        }
    }
}
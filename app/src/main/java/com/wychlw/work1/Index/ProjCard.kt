package com.wychlw.work1.Index

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wychlw.work1.AppState
import com.wychlw.work1.CurrentView
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.functional.getStatIcon

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
            .fillMaxWidth(),
        onClick = {
            val appState = AppState.getInstance()
            appState.value.navController.navigate(CurrentView.ItemDetail + "?iid=${item.iid}&cid=${item.cid}&id=${item.id}&title=${item.title}&status=${item.status}&assign=${item.assign}")
            ///${item.iid}/${item.cid}/${item.id}/${item.title}/${item.status}/${item.assign}
        }
    ) {
        Row {
            val iconI = getStatIcon(item.status)
            val iconIm = iconI.first
            val iconCo = iconI.second
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
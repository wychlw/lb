package com.wychlw.work1.functional

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Info
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

fun getStatIcon(stat: Int): Pair<ImageVector, Color>{
    val iconIm = when (stat) {
        0 -> Icons.Outlined.Circle
        1 -> Icons.Outlined.Info
        2 -> Icons.Outlined.CheckCircle
        3 -> Icons.Outlined.Cancel
        else -> Icons.Outlined.AddCircle
    }
    val iconCo = when (stat) {
        0 -> Color.Gray
        1 -> Color(0xFF2E8B57)
        2 -> Color(0xFFDA70D6)
        3 -> Color.Gray
        else -> Color.Gray
    }
    return Pair(iconIm, iconCo)
}

fun getStatText(stat: Int): String {
    return when (stat) {
        0 -> "Draft"
        1 -> "In Progress"
        2 -> "Completed"
        3 -> "Cancelled"
        else -> "Unknown"
    }
}
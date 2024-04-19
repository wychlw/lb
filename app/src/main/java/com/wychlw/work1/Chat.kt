package com.wychlw.work1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import kotlin.math.min


class Chat : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           AppTheme {
                ChatView()
           }
        }
    }
}

@Composable
fun ChatHeaderDefaultAvatar(modifier: Modifier = Modifier, chat_name: String = "N") {
    val color_primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val inside_text = chat_name.substring(0, 1)
    Box(
        modifier = modifier
            .padding(0.dp)
            .drawWithCache {
                val circle_shape = RoundedPolygon(
                    numVertices = 8,
                    centerX = size.width / 2,
                    centerY = size.height / 2,
                    radius = min(size.height, size.width) / 2,
                    rounding = CornerRounding(
                        min(size.height, size.width) / 2
                    )
                )
                val circle_path = circle_shape
                    .toPath()
                    .asComposePath()
                onDrawBehind {
                    drawPath(circle_path, color = color_primaryContainer)
                }
            }
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = inside_text,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(modifier: Modifier = Modifier, chat_name: String, avatar: ((Modifier) -> Unit)? = null) {
    TopAppBar(
        title = {
            avatar?.invoke(modifier) ?: ChatHeaderDefaultAvatar(modifier, chat_name)
        }
    )
}

@Composable
fun ChatView(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        ChatHeader(chat_name = "TEST")
    }
}

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    AppTheme {
        ChatView()
    }
}
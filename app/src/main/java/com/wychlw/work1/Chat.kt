package com.wychlw.work1

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.tooling.preview.Preview


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
fun ChatHeaderDefaultAvatar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .drawWithCache {
                val circle = RoundedPolygen()
            }
    ) {
        Text(text = "T")
    }
}

@Composable
fun ChatHeader(modifier: Modifier = Modifier, chat_name: String) {
    ChatHeaderDefaultAvatar()
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
package com.wychlw.work1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class index : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                IndexView()
            }
        }
    }
}

@Composable
fun IndexView(modifier: Modifier = Modifier) {
    var test by remember { mutableStateOf(true) }
    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ){
            Column(
                modifier,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "From lw:"
                )
                Text(
                    text = "Hello, World!",
                    fontSize = 50.sp,
                    lineHeight = 56.sp,
                    textAlign = TextAlign.Center,
                    modifier = modifier
                        .padding(16.dp)
                )
                Text(
                    text = if (test) "Hello, World!" else "Hello, Android!",
                    textAlign = TextAlign.Center,
                )
                Button(
                    onClick = { test = !test }
                ) {
                    Text("Click me")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndexPreview() {
    AppTheme {
        IndexView()
    }
}
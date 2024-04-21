package com.wychlw.work1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wychlw.work1.AddItem.AddItemView
import com.wychlw.work1.AddItem.initAddItemUiState
import com.wychlw.work1.Index.IndexUiState
import com.wychlw.work1.Index.IndexView

class IndexActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IndexActivityCreate()
        }
    }
}
@Composable
fun IndexActivityCreate(modifier: Modifier = Modifier) {


    val indexState = IndexUiState.getInstance()
    val navController = rememberNavController()
    AppState.getInstance(navController) // To init the global nav state

    AppTheme {
        NavHost(
            navController = navController,
            startDestination = CurrentView.Index,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            },
            exitTransition = {
                ExitTransition.None
            },
            popExitTransition = {
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            },
            popEnterTransition = {
                EnterTransition.None
            }
        ) {
            composable(route = CurrentView.Index) {
                IndexView(modifier = modifier, state = indexState)
            }
            composable(route = CurrentView.AddItem) {
                AddItemView(modifier = modifier, state = initAddItemUiState())
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IndexActivityPreview() {
    val navController = rememberNavController()
    AppState.getInstance(navController)

    AppTheme {

//                IndexView(state = IndexUiState.getInstance())

                AddItemView(state = initAddItemUiState())
    }
}
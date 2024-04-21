package com.wychlw.work1

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController

class CurrentView {
    companion object {
        val Index = "index"
        val AddItem = "additem"
    }
}

data class AppState(
    var navController: NavController,
) {
    companion object {
        private var instance: MutableState<AppState>? = null
        @Composable
        fun getInstance(navController: NavController?): MutableState<AppState> {
            if (instance == null) {
                val currentViewInit = remember {
                    mutableStateOf(CurrentView.Index)
                }
                if (navController == null) {
                    throw RuntimeException("You should Init global state!!")
                }
                instance = remember {
                    mutableStateOf(AppState(
                        navController
                    ))
                }
            }
            return instance!!
        }

        fun getInstance(): MutableState<AppState> {
            if (instance == null) {
                throw RuntimeException("You should Init global state!!")
            }
            return instance!!
        }
    }
}

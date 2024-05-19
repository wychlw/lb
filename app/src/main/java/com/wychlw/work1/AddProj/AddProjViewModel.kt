package com.wychlw.work1.AddProj

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wychlw.work1.AppState

class ProjType {
    companion object {
        fun values(): Array<String> {
            return arrayOf(LOCAL, REMOTE)
        }

        const val LOCAL = "local"
        const val REMOTE = "remote"
    }
}

// union
data class ProjLocal (
    var name: MutableState<String> = mutableStateOf(""),
)

data class ProjRemote (
    var user: MutableState<String> = mutableStateOf(""),
    var remote_id: MutableState<Int> = mutableStateOf(0),
    var token: MutableState<String> = mutableStateOf("")
)

class ProjDataUnion {
    var local: ProjLocal = ProjLocal()
    var remote: ProjRemote = ProjRemote()
}

data class AddProjUiState(
    val appState: MutableState<AppState>,
    val projType: MutableState<String>,

    val projData: MutableState<ProjDataUnion>
)

@Composable
fun initAddProjUiState(): MutableState<AddProjUiState> {
    val appState = AppState.getInstance()
    return remember {
        mutableStateOf(AddProjUiState(
            appState = mutableStateOf(appState.value),
            projType = mutableStateOf(ProjType.LOCAL),
            projData = mutableStateOf(ProjDataUnion())
        ))
    }
}

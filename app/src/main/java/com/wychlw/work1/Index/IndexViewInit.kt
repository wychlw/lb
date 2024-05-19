package com.wychlw.work1.Index

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjItemDb
import com.wychlw.work1.data.tryInit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun fillIndexUiState(context: Context, state: MutableState<IndexUiState>, scope: CoroutineScope) {
    scope.launch {
        println("Try init database")
        tryInit(state.value.context!!)
        val dao = ProjDatabase.getInstance(state.value.context!!).projDao()
        launch {
            dao.getAllProj().collect() {
                println("Begin Collect Projs")
                state.value.projList.value = it
                state.value.currentProj.value = it[0]
                dao.getProjCol(state.value.currentProj.value.pid).collect() {
                    println("Begin Collect Cols")
                    state.value.currentColList.value = it
                    state.value.currentCol.value = it[0]
                    dao.getProjItem(state.value.currentCol.value.cid).collect() {
                        println("Begin Collect Items")
                        state.value.currentItemList.value = it
                    }
                }
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun initIndexUiState(context: Context): MutableState<IndexUiState> {
    val exampleProj = remember {
        mutableStateOf(ProjDb(1, "Dummy", 1))
    }
    val exampleProjList = remember {
        mutableStateOf(listOf(exampleProj.value))
    }
    val exampleColDraft = remember {
        mutableStateOf(ProjColDb(1, 1, "Draft"))
    }
    val exampleColInprog = remember {
        mutableStateOf(ProjColDb(2, 1, "In Progress"))
    }
    val exampleColFin = remember {
        mutableStateOf(ProjColDb(3, 1, "Done"))
    }
    val exampleColList = remember {
        mutableStateOf(listOf(exampleColDraft.value, exampleColInprog.value, exampleColFin.value))
    }
    val exampleItemList = remember {
        mutableStateOf(listOf<ProjItemDb>(
            ProjItemDb(0,0,0,"TT",0,""),
            ProjItemDb(0,0,0,"TT2",0,"")
        ))
    }

    val state = remember {
        mutableStateOf(
            IndexUiState(
                context,
                exampleProjList,
                exampleProj,
                exampleColList,
                exampleColDraft,
                exampleItemList
            )
        )
    }

    val scope = rememberCoroutineScope()
    fillIndexUiState(context, state, scope)

    return state
}
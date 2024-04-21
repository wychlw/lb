package com.wychlw.work1.data

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.wychlw.work1.data.ProjColDb
import com.wychlw.work1.data.ProjDatabase
import com.wychlw.work1.data.ProjDb
import com.wychlw.work1.data.ProjItemDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

suspend fun tryInit(context: Context) {
    val dao = ProjDatabase.getInstance(context).projDao()
    dao.getAllProj().collect() {
        println("Current init stat: ${it.isNotEmpty()}")
        if (it.isEmpty()) {
            println("Need to init!")
            dao.insertProj(
                ProjDb(0, "Local", 1)
            )
            dao.insertProjCol(
                ProjColDb(0, 1, "Draft")
            )
            dao.insertProjCol(
                ProjColDb(0, 1, "In Progress")
            )
            dao.insertProjCol(
                ProjColDb(0, 1, "Done")
            )
        }
    }
}
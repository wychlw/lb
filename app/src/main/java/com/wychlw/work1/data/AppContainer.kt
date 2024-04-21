package com.wychlw.work1.data

import android.content.Context

interface AppContainer {
    val projRepository: ProjRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val projRepository: ProjRepository by lazy {
        OfflineProjRepository(ProjDatabase.getInstance(context).projDao())
    }
}
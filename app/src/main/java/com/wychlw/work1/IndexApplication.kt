package com.wychlw.work1

import android.app.Application
import com.wychlw.work1.data.AppContainer
import com.wychlw.work1.data.AppDataContainer

class IndexApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
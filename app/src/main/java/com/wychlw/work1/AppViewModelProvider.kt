package com.wychlw.work1

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.wychlw.work1.Index.ProjListViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ProjListViewModel(indexApplication().container.projRepository)
        }
    }
}

fun CreationExtras.indexApplication(): IndexApplication = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as IndexApplication)
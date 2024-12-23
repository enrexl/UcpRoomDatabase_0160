package com.example.ersa.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ersa.KrsApp
import com.example.ersa.ui.view.dosen.HomeDosenView
import com.example.ersa.ui.view.dosen.InsertDosenView

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeDosenViewModel(
                KrsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            InsertDosenViewModel(
                KrsApp().containerApp.repositoryDosen
            ) }
    }
}


fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)

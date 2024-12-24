package com.example.ersa.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ersa.KrsApp
import com.example.ersa.ui.view.matakuliah.UpdateMKView
import com.example.ersa.ui.viewmodel.dosen.HomeDosenViewModel
import com.example.ersa.ui.viewmodel.dosen.InsertDosenViewModel
import com.example.ersa.ui.viewmodel.matakuliah.DetailMKViewModel
import com.example.ersa.ui.viewmodel.matakuliah.HomeMKViewModel
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ersa.ui.viewmodel.matakuliah.UpdateMKViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            InsertDosenViewModel(
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeMKViewModel(
                krsApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            MataKuliahViewModel(
                krsApp().containerApp.repositoryMataKuliah,
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah,
                krsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            DetailMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repositoryMataKuliah
            )
        }


    }
}


fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)

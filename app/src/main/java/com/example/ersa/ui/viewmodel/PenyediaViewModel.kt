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
                KrsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            InsertDosenViewModel(
                KrsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            HomeMKViewModel(
                KrsApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            MataKuliahViewModel(
                KrsApp().containerApp.repositoryMataKuliah,
                //KrsApp().containerApp.repositoryDosen
            )
        }

        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryMataKuliah
            )
        }

        initializer {
            DetailMKViewModel(
                createSavedStateHandle(),
                KrsApp().containerApp.repositoryMataKuliah
            )
        }


    }
}


fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)

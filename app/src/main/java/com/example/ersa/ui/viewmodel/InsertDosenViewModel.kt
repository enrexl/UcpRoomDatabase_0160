package com.example.ersa.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ersa.repository.RepositoryDosen
import com.example.ersa.data.entity.Dosen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class InsertDosenViewModel (private val repositoryDosen: RepositoryDosen) : ViewModel(){

    var uiState by mutableStateOf(DosenUIState())

    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent
        )
    }

    private fun validateFields()
}

data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null
)

data class FormErrorState(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
){
    fun isValid(): Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}

//data class Variable yang menyimpan data input form
data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)

fun DosenEvent.toDosenEntity(): Dosen = Dosen(
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)
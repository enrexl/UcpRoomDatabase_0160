package com.example.ersa.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ersa.data.entity.Dosen

import com.example.ersa.data.entity.MataKuliah
import com.example.ersa.repository.RepositoryDosen
import com.example.ersa.repository.RepositoryMataKuliah
import com.example.ersa.ui.navigation.DestinasiUpdateMataKuliah
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel(){

    var updateUIState by mutableStateOf(MataKuliahUIState())
        private set

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    private val  _kode: String = checkNotNull(savedStateHandle[DestinasiUpdateMataKuliah.KODE])

    init {
        viewModelScope.launch{
            updateUIState=repositoryMataKuliah.getOneMataKuliah(_kode)
                .filterNotNull()
                .first()
                .toUIStateMK()
        }

        viewModelScope.launch{
            val dosenListRepo = repositoryDosen.getAllDosen().first()
                dosenList = dosenListRepo
            updateUIState = updateUIState.copy(dosenList = dosenList)
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent){
        updateUIState = updateUIState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }

    fun validateFields(): Boolean{
        val event = updateUIState.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "KODE MK tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis  = if (event.jenis.isNotEmpty()) null else "Jenis MK tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",

        )
        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.mataKuliahEvent

        if (validateFields()){
            viewModelScope.launch{
                try {
                    repositoryMataKuliah.updateMataKuliah(currentEvent.toMataKuliahEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage Diatur: $(updateUIState.snackBarMessage)")

                }
                catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data Gagal Diupdate"
                    )
                }
            }
        }
        else{
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data Gagal Diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUIState = updateUIState.copy(snackBarMessage = null)
    }
}

fun MataKuliah.toUIStateMK(): MataKuliahUIState = MataKuliahUIState(
    mataKuliahEvent = this.toDetailUiEvent()
)
package com.example.ersa.ui.viewmodel.matakuliah

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.ersa.data.entity.MataKuliah
import com.example.ersa.repository.RepositoryMataKuliah
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.ersa.data.entity.Dosen
import com.example.ersa.repository.RepositoryDosen
import kotlinx.coroutines.launch

class MataKuliahViewModel (
    private val repositoryMataKuliah: RepositoryMataKuliah,
    private val repositoryDosen: RepositoryDosen
) : ViewModel(){

    var uiState by mutableStateOf(MataKuliahUIState())
        private set

    var dosenList by mutableStateOf<List<Dosen>>(emptyList())
        private set

    init {
        viewModelScope.launch{
            repositoryDosen.getAllDosen().collect{dosenList ->
                this@MataKuliahViewModel.dosenList = dosenList
                updateUiState()
            }
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )


    }

    fun validateFields(): Boolean{
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorState(
            kode = if (event.kode.isNotEmpty()) null else "KODE MK tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            semester = if (event.semester.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis MK tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen Pengampu tidak boleh kosong",
        )

        uiState = uiState.copy(isEntryValid = errorState)
    return errorState.isValid()
    }

    fun saveData(){
        val currentEvent = uiState.mataKuliahEvent
        if (validateFields()){
            viewModelScope.launch{
                try {
                    repositoryMataKuliah.insertMataKuliah(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data Berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorState()
                    )
                }
                catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data Gagal Disimpan"
                    )
                }
            }
        }
        else{
            uiState = uiState.copy(
                snackBarMessage = "Input TIdak Valid. Perika Kembali"
            )
        }
    }

    fun resetSnackBarMessage(){
        uiState = uiState.copy(
            snackBarMessage = null
        )
    }

    private fun updateUiState(){
        uiState = uiState.copy(dosenList = dosenList)
    }
}


data class MataKuliahUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
    val dosenList: List<Dosen> = emptyList()
)

data class FormErrorState(
    val kode: String? = null,
    val nama: String? = null,
    val semester: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null

){
    fun isValid(): Boolean{
        return kode == null && nama == null &&
                semester == null && jenis == null && dosenPengampu == null
    }
}

//data class Variable yang menyimpan data input form
data class MataKuliahEvent(
    val kode: String = "",
    val nama: String = "",
    val semester: String = "",
    val jenis: String = "", //mk wajib atau mk pilihan
    val dosenPengampu: String = ""
)

// Menyimpan input form ke dalam activity
fun MataKuliahEvent.toMataKuliahEntity(): MataKuliah = MataKuliah(
    kode = kode,
    nama = nama ,
    semester = semester,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)
package com.example.ersa.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ersa.data.entity.MataKuliah
import com.example.ersa.repository.RepositoryMataKuliah
import com.example.ersa.ui.navigation.DestinasiDetailMataKuliah
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMataKuliah: RepositoryMataKuliah
): ViewModel(){

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiDetailMataKuliah.KODE])

    val detailUiState: StateFlow<DetailUiState> = repositoryMataKuliah.getOneMataKuliah(_kode)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch { emit(
            DetailUiState(
                isLoading = false,
                isError = true,
                errorMessage = it.message ?: "Terjadi Kesalahan"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            )
        )
    fun deleteMhs(){
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch{
                repositoryMataKuliah.deleteMataKuliah(it)
            }
        }
    }

}

data class DetailUiState(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}

//data class untuk menampung data yang akan ditampilkan di UI

//memindahkan data dari entity ke UI
fun MataKuliah.toDetailUiEvent(): MataKuliahEvent{
    return MataKuliahEvent(
        kode = kode,
        nama = nama,
        semester = semester,
        jenis = jenis,
        dosenPengampu = dosenPengampu)

}

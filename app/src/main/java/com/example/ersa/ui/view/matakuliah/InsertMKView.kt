package com.example.ersa.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ersa.ui.customwidget.CustomTopAppBar
import com.example.ersa.ui.viewmodel.PenyediaViewModel

import com.example.ersa.ui.viewmodel.matakuliah.FormErrorState
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahEvent
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahUIState
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahViewModel
import kotlinx.coroutines.launch

@Composable
fun InsertMKView(
    onBack:() -> Unit,
    onNavigate:() -> Unit,
    modifier: Modifier = Modifier,
    //viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory), //initialisasi viewModel: com.ersaditya.ui.viewmodel.MahasiswaViewModel
    viewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState //ambil ui state dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    val coroutineScope = rememberCoroutineScope()

    //Observasi perubahan snackBarMessage
    LaunchedEffect(uiState.snackBarMessage)
    {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch{
                snackbarHostState.showSnackbar(message) // Tampilkan snackBar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)} //tempatkan snackbar di scaffold
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliahh"
            )
            //Isi Body
            InsertBodyMK(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) //update state di vieemodel
                },
                onClick = {
                    coroutineScope.launch{
                        viewModel.saveData() // simpan data
                    }
                    onNavigate()
                }
            )

        }
    }
}

@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUIState,
    onClick: () -> Unit
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMatakuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormMatakuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    val jenisKelamin = listOf("Laki-laki", "Perempuan")
    val jenisMataKuliah = listOf("Peminatan", "Wajib")


    Column(modifier = modifier.fillMaxWidth())
    {
        OutlinedTextField(modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(kode = it))

            },
            label = { Text("Kode")},
            isError = errorState.kode != null,
            placeholder = {Text("Masukkan Kode")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kode ?:"",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = {Text("NAMA")},
            isError = errorState.nama != null,
            placeholder = {Text("Masukkan Nama")},

        )
        Text(text = errorState.nama ?: "",
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis")
        Row(modifier = Modifier.fillMaxWidth())
        {
            jenisMataKuliah.forEach{ jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ){
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jk,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jk))
                        }
                    )
                    Text(
                        text = jk,
                    )
                }
            }
        }
        Text(
            text = errorState.jenis ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mataKuliahEvent.semester,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(semester = it))
            },
            label = {Text("Semester")},
            isError = errorState.semester != null,
            placeholder = {Text("Masukkan Semester")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(text = errorState.semester ?: "",
            color = Color.Red
        )

        //dosen pengampu




    }

}
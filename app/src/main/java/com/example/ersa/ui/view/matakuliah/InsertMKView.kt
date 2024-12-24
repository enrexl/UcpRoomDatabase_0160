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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ersa.data.entity.Dosen
import com.example.ersa.ui.customwidget.CustomTopAppBar
import com.example.ersa.ui.viewmodel.PenyediaViewModel


import com.example.ersa.ui.viewmodel.matakuliah.FormErrorState
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahEvent
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahUIState
import com.example.ersa.ui.viewmodel.matakuliah.MataKuliahViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        topBar = {
            CustomTopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah MataKuliahh"
            )
        },
        modifier = Modifier,
        snackbarHost = {SnackbarHost(hostState = snackbarHostState)} //tempatkan snackbar di scaffold
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
        ) {
            //Isi Body
            InsertBodyMK(
                uiState = uiState,
                onValueChange = { updatedEvent ->
                    viewModel.updateState(updatedEvent) //update state di vieemodel
                },
                onClick = {
                    coroutineScope.launch{
                        if (viewModel.validateFields()){
                            viewModel.saveData() // simpan data
                            delay(600)
                            withContext(Dispatchers.Main){
                                onNavigate()
                            }
                        }

                    }

                },
                dosenList = uiState.dosenList
            )

        }
    }
}

@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUIState,
    onClick: () -> Unit,
    dosenList: List<Dosen>
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
            modifier = Modifier.fillMaxWidth(),
            dosenList = dosenList
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormMatakuliah(
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    dosenList: List<Dosen>
){
    val jenisMataKuliah = listOf("Peminatan", "Wajib")
    val semester = listOf("Ganjil", "Genap")


    var selectedJenis by remember { mutableStateOf(mataKuliahEvent.jenis) }
    var expandedJenis by remember { mutableStateOf(false) }

    var selectedSem by remember { mutableStateOf(mataKuliahEvent.semester) }
    var expandedSem by remember { mutableStateOf(false) }

    var selectedDosen by remember { mutableStateOf(mataKuliahEvent.dosenPengampu) }
    var expandedDosen by remember { mutableStateOf(false) }


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
            //keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.kode ?:"",
            color = Color.Red
        )
        //Nama
        OutlinedTextField(
            modifier = modifier.fillMaxWidth(),
            value = mataKuliahEvent.nama,
            onValueChange = {
                onValueChange(mataKuliahEvent.copy(nama = it))
            },
            label = {Text("Nama MataKuliah")},
            isError = errorState.nama != null,
            placeholder = {Text("Masukkan Nama")},

        )
        Text(text = errorState.nama ?: "",
            color = Color.Red
        )
//        // SKS
//        OutlinedTextField(
//            modifier = modifier.fillMaxWidth(),
//            value = mataKuliahEvent.nama,
//            onValueChange = {
//                onValueChange(mataKuliahEvent.copy(nama = it))
//            },
//            label = {Text("Jumlah SKS")},
//            isError = errorState.nama != null,
//            placeholder = {Text("Masukkan Jumlah SKS")},
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//
//            )
//        Text(text = errorState.nama ?: "",
//            color = Color.Red
//        )

        //JENIS
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis MK")
        Row(modifier = Modifier.fillMaxWidth())
        {
            jenisMataKuliah.forEach{ jk ->
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jk,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jk))
                        }
                    )
                    Text(
                        text = jk,
                        modifier = Modifier.padding(8.dp)

                    )
                }
            }
        }
        Text(
            text = errorState.jenis ?: "",
            color = Color.Red
        )

        //SEMESTER
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Semester")
        ExposedDropdownMenuBox(
            expanded = expandedSem,
            onExpandedChange = { expandedSem = !expandedSem })
        {
            OutlinedTextField(
                readOnly = true,
                value = selectedSem,
                onValueChange = {},
                label = { Text("Pilih Semester") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSem)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                isError = errorState.semester != null
            )
            ExposedDropdownMenu(
                expanded = expandedSem,
                onDismissRequest = { expandedSem = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                semester.forEach { sem ->
                    DropdownMenuItem(
                        text = { Text(sem) },
                        onClick = {
                            selectedSem = sem
                            onValueChange(mataKuliahEvent.copy(semester = sem))
                            expandedSem = false
                        }
                    )
                }
            }
        }
        Text(
            text = errorState.semester ?: "",
            color = Color.Red
        )

        //Dosen Pengampu
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Dosen Pengampu")
        ExposedDropdownMenuBox(expanded = expandedDosen, onExpandedChange = { expandedDosen = !expandedDosen }) {
            TextField(
                readOnly = true,
                value = selectedDosen,
                onValueChange = {},
                label = { Text("Dosen Pengampu") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDosen)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                isError = errorState.semester != null
            )
            ExposedDropdownMenu(
                expanded = expandedDosen,
                onDismissRequest = { expandedDosen = false },
                modifier = modifier.fillMaxWidth()
            ) {
                dosenList.forEach { dosen ->
                    DropdownMenuItem(
                        text = { Text(text = dosen.nama)},
                        onClick = {
                            selectedDosen = dosen.nama
                            onValueChange(mataKuliahEvent.copy(dosenPengampu = dosen.nama))
                            expandedDosen = false
                        }
                    )
                }
            }
        }
        Text(
            text = errorState.dosenPengampu ?: "",
            color = Color.Red
        )






















    }

}
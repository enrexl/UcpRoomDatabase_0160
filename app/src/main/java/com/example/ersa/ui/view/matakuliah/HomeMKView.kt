package com.example.ersa.ui.view.matakuliah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.ersa.data.entity.MataKuliah
import com.example.ersa.ui.customwidget.CustomTopAppBar
import com.example.ersa.ui.viewmodel.matakuliah.HomeMKViewModel
import com.example.ersa.ui.viewmodel.matakuliah.HomeUiStateMK
import com.example.ersa.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

@Preview (showBackground = true)
@Composable
fun HomeMKView(
    viewModel: HomeMKViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddMhs:() -> Unit = {},
    onDetailClick: (String) -> Unit ={},
    onBack:() -> Unit = {},
    modifier: Modifier = Modifier
){
    Scaffold(
        topBar = {
            CustomTopAppBar(
                judul = "Daftar MataKuliah",
                showBackButton = true,
                onBack = {onBack},
                modifier = modifier
            )

        },
        floatingActionButton = {                // tombol add di kanan bawah
            FloatingActionButton(
                onClick = onAddMhs,
                shape = MaterialTheme.shapes.medium,
                modifier = modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah MataKuliah"
                )
            }
        }
    ){ innerPadding -> val homeUiState by viewModel.homeUIState.collectAsState()

        if (homeUiState == null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = "Loading state is null")
            }
        } else {
            BodyHomeMKView(
                homeUiStateMK = homeUiState,
                onClick = onDetailClick,
                modifier = modifier.padding(innerPadding)
            )
        }
//        val homeUiState by viewModel.homeUIState.collectAsState()
//
//        BodyHomeMKView(
//            homeUiStateMK = homeUiState,
//            onClick = {
//                onDetailClick(it)
//            },
//            modifier = modifier.padding(innerPadding)
//        )

    }
}


@Composable
fun BodyHomeMKView(
    homeUiStateMK: HomeUiStateMK,
    onClick: (String) -> Unit = { },
    modifier: Modifier = Modifier
){
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when{
        homeUiStateMK.isLoading ->{
            //menampilkan indikator loading
            Box(modifier = Modifier.fillMaxSize()
                , contentAlignment = Alignment.Center){
                CircularProgressIndicator() }
        }

        homeUiStateMK.isError->{
            //Menampilkan Pesan Error
            LaunchedEffect(homeUiStateMK.errorMessage) {
                homeUiStateMK.errorMessage?.let { message ->
                    coroutineScope.launch{
                        snackbarHostState.showSnackbar(message) //tampilkan snackbar
                    }
                }
            }
        }

        homeUiStateMK.listMK.isEmpty() -> {
            //Menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Tidak ada data MataKuliah",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }else ->{

        ListMataKuliah(
            listMK = homeUiStateMK.listMK,
            onClick = {
                onClick(it)
            },
            modifier = modifier
        )
    } }


}

@Composable
fun ListMataKuliah(
    listMK: List<MataKuliah>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = { }
){
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listMK,
            itemContent = {mk ->
                CardMK(
                    mk = mk,
                    onClick = {onClick(mk.kode)}
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardMK(
    mk: MataKuliah,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
){
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = mk.nama  ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    //color = MaterialTheme.colorScheme.primary
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                HorizontalDivider()

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = mk.dosenPengampu,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = "Semester " + mk.semester,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}
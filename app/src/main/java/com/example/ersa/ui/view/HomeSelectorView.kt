package com.example.ersa.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Preview(showBackground = false)
@Composable
fun HomeSelectorView(
    //onDosenClick:()-> Unit={},
    //onMahasiswaClick:()-> Unit={},
    modifier: Modifier = Modifier
){
    Column(
        modifier = Modifier.fillMaxWidth(),
        Arrangement.Center


    ) {
        Button(
            onClick = {},
        ) {
            Text(text = "DOSEN")
        }
        Button(
            onClick = {},
        ) {
            Text(text = "Mahasiswa")
        }
    }
//    Scaffold(
//        topBar = { CustomTopAppBar(
//            judul = "KRS APP",
//            showBackButton = false,
//            onBack = {},
//            modifier = modifier
//        ) }
//    ){//innerPadding ->
//        //val homeUiState by viewModel.homeUIState.collectAsState()
//        //BodyHomeSelectorView(
//          //  Modifier = modifier.padding()
//        )
//    }
}


@Composable
fun BodyHomeSelectorView(
    modifier: Modifier
){
    Column(
        modifier = modifier.fillMaxWidth()
    ) {  }
}
package com.example.ersa.ui.view


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ersa.ui.customwidget.CustomTopAppBar


@Preview(showBackground = false)
@Composable
fun HomeSelectorView(
    onDosenClick:()-> Unit={},
    onMahasiswaClick:()-> Unit={},
    modifier: Modifier = Modifier
){

    Scaffold(
        topBar = { CustomTopAppBar(
            judul = "KRS APP",
            showBackButton = false,
            onBack = {},
            modifier = modifier
        ) }
    ){innerPadding ->
        BodyHomeSelectorView(
            modifier = modifier.padding(innerPadding),
            onDosenClick = onDosenClick,
            onMhsClick = onMahasiswaClick
        )
    }
}


@Composable
fun BodyHomeSelectorView(
    modifier: Modifier,
    onDosenClick: () -> Unit,
    onMhsClick: () -> Unit

){
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center


    ) {

        Button(
            onClick = onDosenClick,
            Modifier.fillMaxWidth()

        ) {
            Text(text = "DOSEN")
        }
        Button(
            onClick = onMhsClick,
            Modifier.fillMaxWidth()
        ) {
            Text(text = "MATAKULIAH")
        }
    }
}
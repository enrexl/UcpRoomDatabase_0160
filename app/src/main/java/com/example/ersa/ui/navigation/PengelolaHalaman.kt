package com.example.ersa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ersa.ui.view.HomeSelectorView


import androidx.navigation.compose.rememberNavController

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

    NavHost(navController = navController,
        startDestination = DestinasiHome.route)
    {
        composable( //Halaman Selector Pertama
            route = DestinasiHome.route
        ){
            HomeSelectorView()
        }

        //Halaman View Dosen
        //Halaman Insert Dosen

        //Halaman View Matakuliah getAll / asc by name
        //Halaman Insert MataKuliah
        //Halaman Detail Matakuliah
        //Halaman Edit Matakuliah
        //Halaman
    }
}
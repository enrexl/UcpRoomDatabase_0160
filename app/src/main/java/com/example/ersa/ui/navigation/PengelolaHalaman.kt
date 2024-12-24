package com.example.ersa.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ersa.ui.view.HomeSelectorView
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ersa.ui.view.dosen.HomeDosenView
import com.example.ersa.ui.view.dosen.InsertDosenView
import com.example.ersa.ui.view.matakuliah.DetailMKView
import com.example.ersa.ui.view.matakuliah.HomeMKView
import com.example.ersa.ui.view.matakuliah.InsertMKView
import com.example.ersa.ui.view.matakuliah.UpdateMKView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier

) {

    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    )
    {
        composable( //Halaman Selector Pertama
            route = DestinasiHome.route
        ) {
            HomeSelectorView(
                onDosenClick = {
                    navController.navigate(DestinasiHomeDosen.route)
                },
                onMahasiswaClick = {
                    navController.navigate(DestinasiHomeMK.route)
                },
                modifier = modifier


            )
        }

        composable( //Home Dosen
            route = DestinasiHomeDosen.route
        ) {
            HomeDosenView(
                onAddDosen = { navController.navigate(DestinasiInsertDosen.route) },
                onBack = {navController.navigate(DestinasiHomeDosen.route)}
                ,modifier = Modifier
            )
        }

        composable( //Insert Dosen
            route = DestinasiInsertDosen.route
        ) {
            InsertDosenView(
                onBack = { navController.popBackStack() },
                onNavigate = { navController.popBackStack() }
            )
        }

        composable( //Home MataKuliah
            route = DestinasiHomeMK.route
        ) {
            HomeMKView(
                onAddMhs = { navController.navigate(DestinasiInsertMataKulaih.route) },
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiDetailMataKuliah.route}/$kode")
                    println(
                        "PengelolaHalaman: kode = $kode"
                    )
                },
                modifier = Modifier,
                onBack = {navController.popBackStack()}
                )
        }

        composable( //Halaman Insert MataKuliah
            route = DestinasiInsertMataKulaih.route
        ) {
            InsertMKView(
                onBack = { navController.popBackStack() },
                onNavigate = {navController.popBackStack() },
                modifier = modifier
            )
        }

        composable( //Halaman Detail Matakuliah
            DestinasiDetailMataKuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMataKuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiDetailMataKuliah.KODE)
            nim?.let { kode ->
                DetailMKView(
                    onBack = {
                        navController.popBackStack()
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    },
                    onEditClick = { navController.navigate("${DestinasiUpdateMataKuliah.route}/$it") }
                )
            }
        }

        composable( //Halaman Edit Matakuliah
            DestinasiUpdateMataKuliah.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailMataKuliah.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMKView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}





package com.example.ersa.ui.navigation


interface AlamatNavigasi
{ val route: String
}

object DestinasiHome: AlamatNavigasi{
    override val route = "home"
}

object DestinasiDetailMataKuliah: AlamatNavigasi{
    override val route = "detail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}

object DestinasiUpdateMataKuliah : AlamatNavigasi{
    override val route = "update"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}
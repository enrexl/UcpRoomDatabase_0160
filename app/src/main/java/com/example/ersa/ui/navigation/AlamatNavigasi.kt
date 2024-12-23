package com.example.ersa.ui.navigation


interface AlamatNavigasi
{ val route: String
}

object DestinasiHome: AlamatNavigasi{
    override val route = "home"
}

object DestinasiHomeDosen: AlamatNavigasi{
    override val route = "homeDosen"
}

object DestinasiInsertDosen: AlamatNavigasi{
    override val route = "insertDosen"
}


object DestinasiHomeMK: AlamatNavigasi{
    override val route = "homeMK"
}

object DestinasiInsertMataKulaih: AlamatNavigasi{
    override val route: String = "insert_mk"
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
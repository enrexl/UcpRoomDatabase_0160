package com.example.ersa.dependenciesinjection

import android.content.Context
import com.example.ersa.data.database.KrsDatabase
import com.example.ersa.repository.LocalRepositoryDosen
import com.example.ersa.repository.LocalRepositoryMataKuliah
import com.example.ersa.repository.RepositoryDosen
import com.example.ersa.repository.RepositoryMataKuliah


interface InterfaceContainerApp{ //semua repo masuk kesini
    val repositoryDosen: RepositoryDosen
    val repositoryMataKuliah: RepositoryMataKuliah
}

class ContainerApp(private val context: Context): InterfaceContainerApp
{
    override val repositoryDosen: RepositoryDosen by lazy {
        LocalRepositoryDosen(KrsDatabase.getDatabase(context).dosenDao()) }

    override val repositoryMataKuliah: RepositoryMataKuliah by lazy {
        LocalRepositoryMataKuliah(KrsDatabase.getDatabase(context).matakuliahDao())
    }
}
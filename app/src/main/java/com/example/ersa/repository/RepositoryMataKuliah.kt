package com.example.ersa.repository

import com.example.ersa.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMataKuliah {

    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    fun getOneMataKuliah(kode: String): Flow<MataKuliah>

    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}
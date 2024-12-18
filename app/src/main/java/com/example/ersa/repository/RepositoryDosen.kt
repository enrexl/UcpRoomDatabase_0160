package com.example.ersa.repository

import com.example.ersa.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDosen {

    suspend fun insertDosen(dosen: Dosen)

    fun getAllDosen(): Flow<List<Dosen>>
}
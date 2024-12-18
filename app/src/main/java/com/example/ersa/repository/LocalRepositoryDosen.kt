package com.example.ersa.repository

import com.example.ersa.data.dao.DosenDao
import com.example.ersa.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDosen(
    private val dosenDao: DosenDao) : RepositoryDosen
 {
     override suspend fun insertDosen(dosen: Dosen){
         dosenDao.insertDosen(dosen)
     }

     override fun getAllDosen(): Flow<List<Dosen>> {
         return dosenDao.getAllDosen()
     }
}
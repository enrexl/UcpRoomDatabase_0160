package com.example.ersa.repository

import com.example.ersa.data.dao.MataKuliahDao
import com.example.ersa.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMataKuliah(
    private val mataKuliahDao: MataKuliahDao) : RepositoryMataKuliah
 {

     override suspend fun insertMataKuliah(mataKuliah: MataKuliah) {
         mataKuliahDao.insertMataKuliah(mataKuliah)
     }

     override fun getAllMataKuliah(): Flow<List<MataKuliah>> {
         return mataKuliahDao.getAllMataKuliah()
     }

     override fun getOneMataKuliah(kode: String): Flow<MataKuliah> {
         return mataKuliahDao.getOneMataKuliah(kode)
     }

     override suspend fun deleteMataKuliah(mataKuliah: MataKuliah) {
         mataKuliahDao.deleteMataKuliah(mataKuliah)
     }

     override suspend fun updateMataKuliah(mataKuliah: MataKuliah) {
         mataKuliahDao.updateMataKuliah(mataKuliah)
     }


}
package com.example.ersa.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ersa.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {

    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM mataKuliah ORDER BY nama ASC") //asc dari nama
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    @Query("SELECT * FROM mataKuliah where kode = :kode") //asc dari nama
    fun getOneMataKuliah(kode: String): Flow<MataKuliah>

    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)
}
package com.example.ersa.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ersa.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

@Dao
interface DosenDao {

    @Insert
    suspend fun insertDosen(dosen: Dosen)

    @Query("SELECT * FROM dosen ORDER BY nama ASC")
    fun getAllDosen(): Flow<List<Dosen>>
}

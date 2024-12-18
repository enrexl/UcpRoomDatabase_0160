package com.example.ersa.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ersa.data.dao.DosenDao
import com.example.ersa.data.dao.MataKuliahDao
import com.example.ersa.data.entity.Dosen
import com.example.ersa.data.entity.MataKuliah

//Define Database dengan tabel Dosen dan MataKuliah
@Database(entities = [Dosen::class, MataKuliah::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase()   {

    abstract fun dosenDao(): DosenDao //Mendefiniskan fungsi untuk mengakses data Dosen
    abstract fun matakuliahDao(): MataKuliahDao //Mendefiniskan fungsi untuk mengakses data MataKuliah

    companion object{
        @Volatile //Memastikan bahwa nilai variable Instance selalu sama di setiap
        private var Instance: com.example.ersa.data.database.KrsDatabase? = null

        fun getDatabase(context: Context): com.example.ersa.data.database.KrsDatabase {
            return (Instance?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    com.example.ersa.data.database.KrsDatabase::class.java, //class database
                    "KrsDatabase" //Nama Database
                )
                    .build().also { Instance = it }
            }
                    )
        }
    }


}
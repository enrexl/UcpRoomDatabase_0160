package com.example.ersa.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mataKuliah")
data class MataKuliah(
    @PrimaryKey
    val kode: String,
    val nama: String,
    val semester: String,
    val jenis: String, //mk wajib atau mk pilihan
    val dosenPengampu: String
)

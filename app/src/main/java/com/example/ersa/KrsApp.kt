package com.example.ersa

import android.app.Application
import com.example.ersa.dependenciesinjection.ContainerApp


class KrsApp: Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimoan

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) //membuat instance
        //instance adalah objek yang dibuak dari class
    }
}
package com.example.ersa

import android.app.Application
import android.util.Log
import com.example.ersa.dependenciesinjection.ContainerApp


class KrsApp: Application() {
    lateinit var containerApp: ContainerApp // Fungsinya untuk menyimoan

    override fun onCreate(){
        super.onCreate()
        containerApp = ContainerApp(this) //membuat instance
        Log.d("KrsApp", "containerApp initialized")
        //instance adalah objek yang dibuat dari class
    }
}
package com.example.monitoringaplikasi

import android.app.Application
import com.example.monitoringaplikasi.dependeciesinjection.ContainerApp
import com.example.monitoringaplikasi.dependeciesinjection.InterfaceContainerApp

class KrsApp : Application() {
    // fungsinya untuk menyimpan instance ContainerApp
    lateinit var  containerApp: ContainerApp

    override fun onCreate(){
        super.onCreate()
        // Membuat instance ContainerApp
        containerApp = ContainerApp(this)
        // instance adalah object yang dibuat dari class
    }
}
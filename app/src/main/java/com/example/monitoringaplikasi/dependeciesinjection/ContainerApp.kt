package com.example.monitoringaplikasi.dependeciesinjection

import android.content.Context
import com.example.monitoringaplikasi.data.database.KrsDatabase
import com.example.monitoringaplikasi.repository.LocalRepositoryMhs
import com.example.monitoringaplikasi.repository.RepositoryMhs

interface InterfaceContainerApp{
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}
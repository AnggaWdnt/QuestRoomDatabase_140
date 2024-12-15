package com.example.monitoringaplikasi.repository

import com.example.monitoringaplikasi.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

//interface semacam panduan
// setelah dari sini lanjut ke LocalRepositoryMhs

interface RepositoryMhs {

    // suspend digunakan untuk operasi yang berat seperti Create, Delete, Insert
    suspend fun insertMhs(mahasiswa: Mahasiswa)
    fun getAllMhs() : Flow<List<Mahasiswa>> //getAllMhs
    fun getMhs(nim: String): Flow<Mahasiswa> //getMhs
    suspend fun deleteMhs(mahasiswa: Mahasiswa) //deleteMhs
    suspend fun updateMhs(mahasiswa: Mahasiswa) //updateMhs
}
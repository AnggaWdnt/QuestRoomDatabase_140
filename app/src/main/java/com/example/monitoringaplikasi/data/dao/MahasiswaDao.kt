package com.example.monitoringaplikasi.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.monitoringaplikasi.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

@Dao
interface MahasiswaDao {
    // memasukan data dengan mengambil tabel mahasiswa
    // fungsi get all data
    @Query("select * from mahasiswa ORDER BY nama ASC")
    fun getAllMahasiswa() : Flow<List<Mahasiswa>>
    // getMahasiswa
    @Query("select * from mahasiswa WHERE nim = :nim")
    fun getMahasiswa(nim: String) : Flow<Mahasiswa>
    // deleteMahasiswa
    @Delete
    suspend fun deleteMahasiswa(mahasiswa: Mahasiswa)
    // updateMahasiswa
    @Update
    suspend fun updateMahasiswa(mahasiswa: Mahasiswa)

    @Insert
        suspend fun insertMahasiswa (
            mahasiswa: Mahasiswa
        )
    }
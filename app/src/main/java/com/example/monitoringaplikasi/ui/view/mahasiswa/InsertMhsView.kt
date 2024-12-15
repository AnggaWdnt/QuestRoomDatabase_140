package com.example.monitoringaplikasi.ui.view.mahasiswa

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.monitoringaplikasi.ui.customwidget.TopAppBar
import com.example.monitoringaplikasi.ui.navigation.AlamatNavigasi
import com.example.monitoringaplikasi.ui.viewmodel.FormErrorState
import com.example.monitoringaplikasi.ui.viewmodel.MahasiswaEvent
import com.example.monitoringaplikasi.ui.viewmodel.MahasiswaViewModel
import com.example.monitoringaplikasi.ui.viewmodel.MhsUIState
import com.example.monitoringaplikasi.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsert : AlamatNavigasi{
    override val route: String = "insert_mhs"
}

@Composable
fun InsertMhsView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MahasiswaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.uiState // Ambil UI dari viewmodel
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    val coroutineScope = rememberCoroutineScope()

    // Observasi Perubahan snackbarMessage
    LaunchedEffect(uiState.snackBarMessage){
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message) // Tampilan snackbar
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ){ padding ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(16.dp)
        ){

            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah mahasiswa",
                modifier = modifier
            )

            // isi body
            InsertBodyMhs(
                uiState = uiState,
                onValueChange = {updateEvent ->
                    viewModel.updateState(updateEvent) // Update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        viewModel.saveData() // Simpan Data
                    }
                    onNavigate()
                }
            )
        }
    }
}
@Composable
fun InsertBodyMhs(
    modifier: Modifier = Modifier,
    onValueChange: (MahasiswaEvent) -> Unit,
    uiState: MhsUIState,
    onClick: () -> Unit
){
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMahasiswa(
            mahasiswaEvent = uiState.mahasiswaEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMahasiswa(
    mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    onValueChange: (MahasiswaEvent) -> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
) {
    val jenisKelamin = listOf("laki-laki", "perempuan")
    val kelas = listOf("A", "B", "C", "D", "E")

    Column (
        modifier = modifier.fillMaxWidth()
    ){

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  mahasiswaEvent.nama,
            onValueChange = {
                onValueChange (mahasiswaEvent.copy(nama = it))
            },
            label = { Text(text = "Nama")},
            isError = errorState.nama != null,
            placeholder =  { Text(text = "Masukan nama")},
        )
        Text(
            text = errorState.nama ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  mahasiswaEvent.nim,
            onValueChange = {
                onValueChange (mahasiswaEvent.copy(nim = it))
            },
            label = { Text(text = "NIM")},
            isError = errorState.nim != null,
            placeholder =  { Text(text = "Masukan NIM")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Text(
            text = errorState.nim ?: "",
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Jenis Kelamin")
        Row (
            modifier = Modifier.fillMaxWidth()
        ){
            jenisKelamin.forEach{ jk ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    RadioButton(
                        selected = mahasiswaEvent.jenisKelamin == jk,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(jenisKelamin = jk))
                        },

                    )
                    Text(
                        text = jk
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  mahasiswaEvent.alamat,
            onValueChange = {
                onValueChange (mahasiswaEvent.copy(alamat = it))
            },
            label = { Text(text = "Alamat")},
            isError = errorState.alamat != null,
            placeholder =  { Text(text = "Masukan Alamat")},
        )
        Text(
            text = errorState.alamat ?: "",
            color = Color.Red
        )
        Spacer(modifier = modifier.height(16.dp))
        Text(text = "Kelas")
        Row {
            kelas.forEach { kelas ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mahasiswaEvent.kelas == kelas,
                        onClick = {
                            onValueChange(mahasiswaEvent.copy(kelas = kelas))
                        },
                    )
                    Text(
                        text = kelas,
                    )
                }
            }
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value =  mahasiswaEvent.angkatan,
            onValueChange = {
                onValueChange (mahasiswaEvent.copy(angkatan = it))
            },
            label = { Text(text = "Angkatan")},
            isError = errorState.angkatan != null,
            placeholder =  { Text(text = "Masukan Angkatan")},
        )
        Text(
            text = errorState.angkatan ?: "",
            color = Color.Red
        )
    }
}
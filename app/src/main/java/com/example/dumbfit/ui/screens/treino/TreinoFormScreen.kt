package com.example.dumbfit.ui.screens.treino

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.dumbfit.viewmodel.TreinoViewModel
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreinoFormScreen(
    viewModel: TreinoViewModel,
    onVoltar: () -> Unit
) {
    var tipo    by remember { mutableStateOf("") }
    var data    by remember { mutableStateOf("") }
    var notas   by remember { mutableStateOf("") }
    var duracao by remember { mutableStateOf("") }

    var erroTipo by remember { mutableStateOf(false) }
    var erroData by remember { mutableStateOf(false) }

    var mostrarDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = null
    )

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    if (mostrarDatePicker) {
        DatePickerDialog(
            onDismissRequest = { mostrarDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            data = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate()
                                .format(formatter)
                            erroData = false
                        }
                        mostrarDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Treino") },
                navigationIcon = {
                    IconButton(onClick = onVoltar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = tipo,
                onValueChange = { tipo = it; erroTipo = false },
                label = { Text("Tipo de treino") },
                placeholder = { Text("Ex: Sparring, Kata, Randori...") },
                isError = erroTipo,
                supportingText = { if (erroTipo) Text("Campo obrigatório") },
                modifier = Modifier.fillMaxWidth()
            )

            // ✅ CORREÇÃO: Box transparente por cima captura o clique
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = data,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Data") },
                    placeholder = { Text("Selecione a data") },
                    isError = erroData,
                    supportingText = { if (erroData) Text("Campo obrigatório") },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Selecionar data"
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { mostrarDatePicker = true }
                )
            }

            OutlinedTextField(
                value = duracao,
                onValueChange = { duracao = it },
                label = { Text("Duração (minutos)") },
                placeholder = { Text("Ex: 60") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notas,
                onValueChange = { notas = it },
                label = { Text("Notas") },
                placeholder = { Text("Como foi o treino?") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    erroTipo = tipo.isBlank()
                    erroData = data.isBlank()

                    if (!erroTipo && !erroData) {
                        viewModel.inserirTreino(
                            data = data,
                            tipo = tipo.trim(),
                            notas = notas.trim(),
                            duracao = duracao.toIntOrNull() ?: 0
                        )
                        onVoltar()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Salvar treino")
            }
        }
    }
}
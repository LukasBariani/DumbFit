package com.example.dumbfit.ui.screens.tecnica

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dumbfit.viewmodel.TecnicaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicaFormScreen(
    viewModel: TecnicaViewModel,
    onVoltar: () -> Unit
) {
    var nome         by remember { mutableStateOf("") }
    var arteMarcial  by remember { mutableStateOf("") }
    var detalhes     by remember { mutableStateOf("") }

    var erroNome        by remember { mutableStateOf(false) }
    var erroArteMarcial by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Técnica") },
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
                value = nome,
                onValueChange = { nome = it; erroNome = false },
                label = { Text("Nome da técnica") },
                placeholder = { Text("Ex: Armlock, Ippon Seoi Nage, Chute Circular...") },
                isError = erroNome,
                supportingText = { if (erroNome) Text("Campo obrigatório") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = arteMarcial,
                onValueChange = { arteMarcial = it; erroArteMarcial = false },
                label = { Text("Arte Marcial") },
                placeholder = { Text("Ex: Jiu-Jitsu, Judô, Karate...") },
                isError = erroArteMarcial,
                supportingText = { if (erroArteMarcial) Text("Campo obrigatório") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = detalhes,
                onValueChange = { detalhes = it },
                label = { Text("Detalhes de Execução") },
                placeholder = { Text("Descreva os detalhes da pegada, ajuste, postura...") },
                minLines = 4,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    erroNome = nome.isBlank()
                    erroArteMarcial = arteMarcial.isBlank()

                    if (!erroNome && !erroArteMarcial) {
                        viewModel.inserirTecnica(
                            nome = nome.trim(),
                            arteMarcial = arteMarcial.trim(),
                            detalhes = detalhes.trim()
                        )
                        onVoltar()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Salvar técnica")
            }
        }
    }
}
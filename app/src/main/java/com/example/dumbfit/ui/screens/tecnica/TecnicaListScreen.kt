package com.example.dumbfit.ui.screens.tecnica

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dumbfit.viewmodel.TecnicaUiState
import com.example.dumbfit.viewmodel.TecnicaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TecnicaListScreen(
    viewModel: TecnicaViewModel,
    onNovaTecnica: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Minhas Técnicas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNovaTecnica) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {

                is TecnicaUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is TecnicaUiState.Error -> {
                    Text(
                        text = state.mensagem,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TecnicaUiState.Success -> {
                    if (state.tecnicas.isEmpty()) {
                        Text(
                            text = "Nenhuma técnica cadastrada ainda.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.tecnicas) { tecnica ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 6.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = tecnica.nome,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            SuggestionChip(
                                                onClick = { },
                                                label = { Text(tecnica.arteMarcial) }
                                            )
                                        }

                                        if (tecnica.descricao.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Text(
                                                text = tecnica.descricao,
                                                style = MaterialTheme.typography.bodyMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
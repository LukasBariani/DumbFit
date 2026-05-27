package com.example.dumbfit.ui.screens.treino


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dumbfit.viewmodel.TreinoUiState
import com.example.dumbfit.viewmodel.TreinoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreinoListScreen(
    viewModel: TreinoViewModel,
    onNovaTreino: () -> Unit          // <-- novo parâmetro
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Diário de Artes Marciais") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNovaTreino) {  // <-- navega ao invés de inserir
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when (val state = uiState) {

                is TreinoUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

                is TreinoUiState.Error -> {
                    Text(
                        text = state.mensagem,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is TreinoUiState.Success -> {
                    if (state.treinos.isEmpty()) {
                        Text(
                            text = "Nenhum treino registrado ainda.",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(state.treinos) { treino ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 6.dp)
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Text(
                                            text = treino.tipoTreino,
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Text(
                                            text = treino.data,
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        if (treino.notas.isNotBlank()) {
                                            Text(
                                                text = treino.notas,
                                                style = MaterialTheme.typography.bodyMedium
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
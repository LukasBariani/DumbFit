package com.example.dumbfit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dumbfit.data.local.entity.TreinoEntity
import com.example.dumbfit.data.repository.TreinoRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TreinoViewModel(private val repository: TreinoRepository) : ViewModel() {

    companion object {
        private const val TAG = "TreinoViewModel"
    }

    private val _uiState = MutableStateFlow<TreinoUiState>(TreinoUiState.Loading)
    val uiState: StateFlow<TreinoUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel iniciado")
        observarTreinos()
    }

    private fun observarTreinos() {
        viewModelScope.launch {
            repository.todosTreinos
                .catch { e ->
                    Log.e(TAG, "Erro no Flow de treinos", e)
                    _uiState.value = TreinoUiState.Error("Erro ao carregar treinos.")
                }
                .collect { lista ->
                    Log.d(TAG, "Flow emitiu ${lista.size} treinos")
                    _uiState.value = TreinoUiState.Success(lista)
                }
        }
    }

    fun inserirTreino(data: String, tipo: String, notas: String, duracao: Int) {
        viewModelScope.launch {
            try {
                repository.inserir(
                    TreinoEntity(
                        data = data,
                        tipoTreino = tipo,
                        notas = notas,
                        duracaoMinutos = duracao
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao inserir", e)
                _uiState.value = TreinoUiState.Error("Não foi possível salvar o treino.")
            }
        }
    }

    fun deletarTreino(treino: TreinoEntity) {
        viewModelScope.launch {
            repository.deletar(treino)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel destruído")
    }
}
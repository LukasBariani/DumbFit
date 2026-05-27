package com.example.dumbfit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dumbfit.data.local.entity.TecnicaEntity // Ajustado para bater com seu padrão de pacotes
import com.example.dumbfit.data.repository.TecnicaRepository // Ajustado para bater com seu padrão de pacotes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// Interface de estados de UI para as Técnicas
sealed interface TecnicaUiState {
    object Loading : TecnicaUiState
    data class Success(val tecnicas: List<TecnicaEntity>) : TecnicaUiState
    data class Error(val mensagem: String) : TecnicaUiState
}

class TecnicaViewModel(private val repository: TecnicaRepository) : ViewModel() {

    companion object {
        private const val TAG = "TecnicaViewModel"
    }

    private val _uiState = MutableStateFlow<TecnicaUiState>(TecnicaUiState.Loading)
    val uiState: StateFlow<TecnicaUiState> = _uiState.asStateFlow()

    init {
        Log.d(TAG, "ViewModel de Técnicas iniciado")
        observarTecnicas()
    }

    private fun observarTecnicas() {
        viewModelScope.launch {
            repository.todasTecnicas
                .catch { e ->
                    Log.e(TAG, "Erro no Flow de técnicas", e)
                    _uiState.value = TecnicaUiState.Error("Erro ao carregar técnicas.")
                }
                .collect { lista ->
                    Log.d(TAG, "Flow emitiu ${lista.size} técnicas")
                    _uiState.value = TecnicaUiState.Success(lista)
                }
        }
    }

    fun inserirTecnica(nome: String, arteMarcial: String, detalhes: String) {
        viewModelScope.launch {
            try {
                repository.inserir(
                    TecnicaEntity(
                        nome = nome,
                        arteMarcial = arteMarcial,
                        descricao = detalhes
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Falha ao inserir técnica", e)
                _uiState.value = TecnicaUiState.Error("Não foi possível salvar a técnica.")
            }
        }
    }

    fun deletarTecnica(tecnica: TecnicaEntity) {
        viewModelScope.launch {
            repository.deletar(tecnica)
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel de Técnicas destruído")
    }
}
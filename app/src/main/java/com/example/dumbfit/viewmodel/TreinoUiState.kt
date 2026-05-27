package com.example.dumbfit.viewmodel

import com.example.dumbfit.data.local.entity.TreinoEntity

sealed class TreinoUiState {
    object Loading : TreinoUiState()
    data class Success(val treinos: List<TreinoEntity>) : TreinoUiState()
    data class Error(val mensagem: String) : TreinoUiState()
}
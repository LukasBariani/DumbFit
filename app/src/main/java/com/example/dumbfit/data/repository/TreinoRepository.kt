package com.example.dumbfit.data.repository

import android.util.Log
import com.example.dumbfit.data.local.dao.TreinoDao
import com.example.dumbfit.data.local.entity.TreinoEntity
import kotlinx.coroutines.flow.Flow

class TreinoRepository(private val dao: TreinoDao) {

    companion object {
        private const val TAG = "TreinoRepository"
    }

    val todosTreinos: Flow<List<TreinoEntity>> = dao.getAllTreinos()

    suspend fun inserir(treino: TreinoEntity) {
        try {
            val id = dao.inserirTreino(treino)
            Log.i(TAG, "Treino inserido | ID: $id | Tipo: ${treino.tipoTreino}")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao inserir treino", e)
            throw e
        }
    }

    suspend fun deletar(treino: TreinoEntity) {
        Log.w(TAG, "Deletando treino ID=${treino.id}")
        dao.deletarTreino(treino)
    }
}
package com.example.dumbfit.data.repository

import android.util.Log
import com.example.dumbfit.data.local.dao.TecnicaDao
import com.example.dumbfit.data.local.entity.TecnicaEntity
import kotlinx.coroutines.flow.Flow

class TecnicaRepository(private val dao: TecnicaDao) {

    companion object {
        private const val TAG = "TecnicaRepository"
    }

    // Retorna o Flow contendo todas as técnicas direto do banco de dados Room
    val todasTecnicas: Flow<List<TecnicaEntity>> = dao.getAllTecnicas()

    suspend fun inserir(tecnica: TecnicaEntity) {
        try {
            val id = dao.inserirTecnica(tecnica)
            Log.i(TAG, "Técnica inserida | ID: $id | Nome: ${tecnica.nome}")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao inserir técnica", e)
            throw e
        }
    }

    suspend fun deletar(tecnica: TecnicaEntity) {
        Log.w(TAG, "Deletando técnica ID=${tecnica.id}")
        dao.deletarTecnica(tecnica)
    }
}
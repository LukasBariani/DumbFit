package com.example.dumbfit.data.local.dao

import androidx.room.*
import com.example.dumbfit.data.local.entity.TreinoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TreinoDao {

    @Query("SELECT * FROM treinos ORDER BY data DESC")
    fun getAllTreinos(): Flow<List<TreinoEntity>>

    @Query("SELECT * FROM treinos WHERE id = :id")
    suspend fun getTreinoById(id: Int): TreinoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTreino(treino: TreinoEntity): Long

    @Update
    suspend fun atualizarTreino(treino: TreinoEntity)

    @Delete
    suspend fun deletarTreino(treino: TreinoEntity)
}
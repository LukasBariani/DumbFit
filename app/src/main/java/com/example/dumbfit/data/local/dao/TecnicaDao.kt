package com.example.dumbfit.data.local.dao

import androidx.room.*
import com.example.dumbfit.data.local.entity.TecnicaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TecnicaDao {

    @Query("SELECT * FROM tecnicas ORDER BY arteMarcial, nome")
    fun getAllTecnicas(): Flow<List<TecnicaEntity>>

    @Query("SELECT * FROM tecnicas WHERE arteMarcial = :arte")
    fun getTecnicasPorArte(arte: String): Flow<List<TecnicaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirTecnica(tecnica: TecnicaEntity): Long

    @Update
    suspend fun atualizarTecnica(tecnica: TecnicaEntity)

    @Delete
    suspend fun deletarTecnica(tecnica: TecnicaEntity)
}
package com.example.dumbfit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "treinos")
data class TreinoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: String,
    val tipoTreino: String,
    val notas: String = "",
    val duracaoMinutos: Int = 0
)
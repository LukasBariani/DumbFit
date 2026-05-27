package com.example.dumbfit.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tecnicas")
data class TecnicaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val arteMarcial: String,
    val nivelDominio: Int = 1,
    val descricao: String = ""
)
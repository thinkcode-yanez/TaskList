package com.thinkcode.tasklist.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tarea_tabla")
data class Tarea(
    val nombre:String,
    val prioridad:Boolean=false,
    var realizado:Boolean=false,
    val fecha:String,
    @PrimaryKey(autoGenerate = true)
    var id:Long
)

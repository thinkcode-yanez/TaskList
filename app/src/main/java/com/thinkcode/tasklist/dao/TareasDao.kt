package com.thinkcode.tasklist.dao

import androidx.room.*
import com.thinkcode.tasklist.models.Tarea


@Dao
interface TareasDao {

    @Query("SELECT * FROM tarea_tabla")
    suspend fun getAll():List<Tarea>

    @Query("SELECT * FROM tarea_tabla WHERE nombre LIKE '%'|| :name || '%'")
    suspend fun getByName(name:String):List<Tarea>

    @Query("SELECT * FROM tarea_tabla WHERE id= :id")
    suspend fun getById(id:Long):Tarea

    @Insert
    suspend fun insertTarea(tarea:List<Tarea>):List<Long>//Devuelve long de las llaves que se iran generando

    @Update
    suspend fun updateTarea(tarea:Tarea):Int

    @Delete
    suspend fun deleteAll(tarea:Tarea):Int


}
package com.thinkcode.tasklist.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thinkcode.tasklist.dao.TareasDao
import com.thinkcode.tasklist.models.Tarea


@Database(
    entities = [Tarea::class],
    version = 1
)
abstract class TareasDatabase :RoomDatabase(){

    abstract fun tareasDao():TareasDao

}
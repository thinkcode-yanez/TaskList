package com.thinkcode.tasklist.config

import android.app.Application
import androidx.room.Room

class TareasApp : Application() {

    companion object {

        lateinit var db: TareasDatabase
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            this,
            TareasDatabase::class.java,
            "tareas"
        ).build()
    }
}
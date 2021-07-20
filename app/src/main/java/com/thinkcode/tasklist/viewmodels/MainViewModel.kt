package com.thinkcode.tasklist.viewmodels

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkcode.tasklist.config.TareasApp.Companion.db
import com.thinkcode.tasklist.models.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    val tareasList = MutableLiveData<List<Tarea>>()
    var busqueda = MutableLiveData<String>()
    val cargaWIN = MutableLiveData<Boolean>()



    fun start() {

        viewModelScope.launch {

            tareasList.value = withContext(Dispatchers.IO) {
                /*  db.tareasDao().insertTarea(arrayListOf<Tarea>(

                       Tarea("Dua sadfksjdf sdlfkjasdflkjsdfk asdlfj", prioridad = true, realizado = true, "2021 07 19",0),
                       Tarea("Jessica sdfasdfsdf sdafsd f sdsdfasdf", prioridad = false, realizado = true, "2021 07 19",0),
                       Tarea("Dua asffsdf sadfsdf asf", prioridad = true, realizado = true, "2021 07 19",0),
                       Tarea("Jessica asdfsf df sf", prioridad = false, realizado = true, "2021 07 19",0),
                       Tarea("Dua", prioridad = true, realizado = true, "2021 07 19",0),
                       Tarea("Jessicaas dfsfsd sf sdfsdfs f", prioridad = false, realizado = true, "2021 07 21",0)

                   ))*/
                db.tareasDao().getAll()
            }!!

            for (tarea in tareasList.value!!) {
                Log.d("mensaje", "Id = ${tarea.id},nombre: ${tarea.nombre} fecja ${tarea.fecha}")
            }
        }
    }

    fun buscarTarea(p0: Editable?) {

        viewModelScope.launch {
            tareasList.value = withContext(Dispatchers.IO) {
                db.tareasDao().getByName(p0.toString())
            }!!
        }
    }

    fun guardarCheck(
        realizado: Boolean,
        nombre: String,
        fecha: String,
        prioridad: Boolean,
        id: Long
    ) {

       var mTarea= Tarea(nombre,prioridad,realizado,fecha,id)


        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                db.tareasDao().updateTarea(mTarea)
            }
            cargaWIN.value=true

        }


        Log.d("mensajeMV",mTarea.toString())
    }


}
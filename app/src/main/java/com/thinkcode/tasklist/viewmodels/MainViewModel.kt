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
    var cargaWIN = MutableLiveData<Boolean>()


    fun start() {

        viewModelScope.launch {

            tareasList.value = withContext(Dispatchers.IO) {

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
        cateoria: String,
        id: Long
    ) {

        var mTarea = Tarea(nombre, prioridad, realizado, fecha, cateoria, id)
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                db.tareasDao().updateTarea(mTarea)
            }
            //  cargaWIN.value=true

        }
        Log.d("mensajeMV", mTarea.toString())
    }

    fun deleteChecked() {
        for (tarea in tareasList.value!!) {
            if (tarea.realizado) {
                val mTarea = Tarea(
                    tarea.nombre,
                    tarea.prioridad,
                    tarea.realizado,
                    tarea.fecha,
                    tarea.category,
                    tarea.id
                )
                viewModelScope.launch {
                    val result = withContext(Dispatchers.IO) {
                        db.tareasDao().deleteAll(mTarea)
                    }
                    cargaWIN.value = (result > 0)
                }

            }
        }
    }

    fun getByPriority() {
        for (tarea in tareasList.value!!) {

            if (tarea.prioridad == true) {
                viewModelScope.launch {
                    tareasList.value = withContext(Dispatchers.IO) {
                        db.tareasDao().getByPrioridad(tarea.prioridad)
                    }!!
                }

            }


        }

    }

    fun mostrarPorCategoria(s: String) {

        val id=s
        viewModelScope.launch {
            tareasList.value= withContext(Dispatchers.IO){
                db.tareasDao().getByCategory(id)
            }!!
        }


    }

    fun ordenarPorFecha():List<Tarea>{
        val tarea= tareasList.value!!.toMutableList()

        tarea.sortBy {it.fecha}
        tarea.reverse()

      return tarea

    }


}
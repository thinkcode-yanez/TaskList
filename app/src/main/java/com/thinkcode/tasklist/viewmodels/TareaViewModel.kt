package com.thinkcode.tasklist.viewmodels

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thinkcode.tasklist.config.Constantes
import com.thinkcode.tasklist.config.TareasApp.Companion.db
import com.thinkcode.tasklist.models.Tarea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TareaViewModel:ViewModel() {

    var id=MutableLiveData<Long>()
    var nombretarea=MutableLiveData<String>()
    val checked:Boolean=false
    var priority=MutableLiveData<Boolean>()
    var fecha=MutableLiveData<String>()

    var operacion= Constantes.OPERACION_INSERTAR
    val operacionExitosa= MutableLiveData<Boolean>()
    var cargaExitosa = MutableLiveData<Boolean>()

    init{

    }
    fun guardarTarea(tareaname: Editable, prioridad: Boolean, fecha: CharSequence) {
        nombretarea.value= tareaname.toString()
        priority.value=prioridad

        if(validarInfo()){
            var mTarea= Tarea(nombretarea.value!!,priority.value!!,checked,fecha.toString(),0)

            when(operacion){
                Constantes.OPERACION_INSERTAR->{

                    viewModelScope.launch {
                        val result= withContext(Dispatchers.IO){
                            db.tareasDao().insertTarea(
                                arrayListOf<Tarea>(
                                    mTarea
                                )
                            )
                        }
                        operacionExitosa.value= result.isNotEmpty()
                    }


                }
                Constantes.OPERACION_EDITAR->{

                    mTarea.id=id.value!!
                    viewModelScope.launch {
                        val result= withContext(Dispatchers.IO){
                            db.tareasDao().updateTarea(mTarea)
                        }
                        operacionExitosa.value=(result>0)
                    }

                }

            }

        }else{

            operacionExitosa.value=false
        }



    }

    fun cargarDatos() {
        viewModelScope.launch {
            var tarea = withContext(Dispatchers.IO){
                db.tareasDao().getById(id.value!!)
            }
            nombretarea.value=tarea.nombre
            fecha.value=tarea.fecha
            priority.value=tarea.prioridad
            cargaExitosa.value=tarea.equals(1)
            Log.d("mensaje2",priority.value.toString())

        }
    }

    fun validarInfo():Boolean{
        return !(nombretarea.value.isNullOrEmpty())


    }
}
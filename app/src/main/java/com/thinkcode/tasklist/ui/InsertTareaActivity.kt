package com.thinkcode.tasklist.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.thinkcode.tasklist.MainActivity
import com.thinkcode.tasklist.config.Constantes
import com.thinkcode.tasklist.databinding.ActivityInsertTareaBinding
import com.thinkcode.tasklist.dialogos.BorrarDialogo
import com.thinkcode.tasklist.viewmodels.TareaViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InsertTareaActivity : AppCompatActivity(), BorrarDialogo.BorrarListener {

    lateinit var binding: ActivityInsertTareaBinding
    val tareaViewModel: TareaViewModel by viewModels()
    val currentDateTime: LocalDateTime? = LocalDateTime.now()
    lateinit var dialogo: BorrarDialogo



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialogo = BorrarDialogo(this)//inicializar dialogo
        binding.tvFechacreada.text = currentDateTime!!.format(DateTimeFormatter.ISO_DATE)

        val actionbar=supportActionBar

        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        //Agregamos una categoria por defaul
        binding.rbOther.isChecked = true

        tareaViewModel.operacion = intent.getStringExtra(Constantes.OPERACION_KEY)!!


        binding.floatSavebutton.setOnClickListener {
            val tareaname = binding.etInputTarea.text
            val prioridad = binding.checkboxImportancia.isChecked
            //Radiobutton
            val checkCategoryGroup = binding.radioGroup.checkedRadioButtonId
            val category = findViewById<RadioButton>(checkCategoryGroup)
            val fecha = binding.tvFechacreada.text.toString()

            tareaViewModel.guardarTarea(tareaname, prioridad, fecha, category)

        }
        binding.btnEditar.setOnClickListener {
            val tareaname = binding.etInputTarea.text
            val prioridad = binding.checkboxImportancia.isChecked
            //Radiobutton
            val checkCategoryGroup = binding.radioGroup.checkedRadioButtonId
            val category = findViewById<RadioButton>(checkCategoryGroup)
            val fecha = binding.tvFechacreada.text.toString()
            tareaViewModel.guardarTarea(tareaname, prioridad, fecha, category)
        }
        binding.btnBorrar.setOnClickListener {
            mostrarDialogo()
        }
        binding.btntest.setOnClickListener {
            val tareaname = binding.etInputTarea.text
            val prioridad = binding.checkboxImportancia.isChecked
            //Radiobutton
            val checkCategoryGroup = binding.radioGroup.checkedRadioButtonId
            val category = findViewById<RadioButton>(checkCategoryGroup)
            val fecha = binding.tvFechacreada.text.toString()

            tareaViewModel.guardarTarea(tareaname, prioridad, fecha, category)
        }

        tareaViewModel.operacionExitosa.observe(this, Observer {
            if (it) {
                mostrarMensaje("Task successfully Saved")
               // irAlInicio()
                clearCamps()

            } else {
                mostrarMensaje("Error ")
            }
        })
        tareaViewModel.operacionExitosaEditar.observe(this, Observer {
            if (it) {
                mostrarMensaje("Task Edited and Saved successfully ")
                 irAlInicio()

            } else {
                mostrarMensaje("Error ")
            }
        })

        if (tareaViewModel.operacion == Constantes.OPERACION_EDITAR) {
           // Toast.makeText(this, "Si entro a editar", Toast.LENGTH_LONG).show()
            tareaViewModel.id.value = intent.getLongExtra(Constantes.ID_TAREA_KEY, 0)
            tareaViewModel.cargarDatos()
            binding.linearEditar.visibility = View.VISIBLE
         //   binding.floatSavebutton.visibility = View.GONE
            binding.btntest.visibility=View.GONE

        } else {
            binding.linearEditar.visibility = View.GONE
           // binding.floatSavebutton.visibility = View.VISIBLE
            binding.btntest.visibility=View.VISIBLE

        }
        tareaViewModel.cargaExitosa.observe(this, Observer {
            binding.etInputTarea.setText(tareaViewModel.nombretarea.value.toString())
            //binding.checkboxImportancia.isSelected=tareaViewModel.priority.value!!
            binding.tvFechacreada.text = tareaViewModel.fecha.value.toString()
            binding.checkboxImportancia.isChecked = (tareaViewModel.priority.value == true)
            checkRadioGroup()


        })


    }

    private fun clearCamps() {
        binding.etInputTarea.text.clear()
        binding.rbOther.isChecked=true
        binding.checkboxImportancia.isChecked=false
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
      // mainViewModel.ordenarPorFecha()
        return true
    }

    private fun checkRadioGroup() {
        val id = tareaViewModel.categoria.value
      //  Toast.makeText(this, "la id es ${id}", Toast.LENGTH_LONG).show()
        when (id) {
            "Work" -> binding.rbWork.isChecked = true
            "Home" -> binding.rbHome.isChecked = true
            "Shopping" -> binding.rbShop.isChecked = true
            "Payments" -> binding.rbPay.isChecked = true
            "Other" -> binding.rbOther.isChecked = true
            else -> {
                Toast.makeText(this, "No se selectiono ninguna Categoria", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun mostrarDialogo() {
        dialogo.show(supportFragmentManager, "Dialogo Borrar")
    }

    private fun irAlInicio() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun mostrarMensaje(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_LONG).show()
    }

    override fun borrarTarea() {
        tareaViewModel.eliminarTarea()
    }
}
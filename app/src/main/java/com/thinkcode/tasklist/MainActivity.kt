package com.thinkcode.tasklist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thinkcode.tasklist.adapters.TareaAdapter
import com.thinkcode.tasklist.config.Constantes
import com.thinkcode.tasklist.databinding.ActivityMainBinding
import com.thinkcode.tasklist.ui.InsertTareaActivity
import com.thinkcode.tasklist.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    val mainViewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.start()


        binding.myMainRecycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

        mainViewModel.tareasList.observe(this, Observer {
            binding.myMainRecycler.adapter=TareaAdapter(it)

        })

        binding.etBuscar.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().isNotEmpty()){
                    mainViewModel.buscarTarea(p0)
                }else if(p0.toString().isEmpty()){
                    mainViewModel.start()
                }
            }
        })

        binding.btnAbrirFormulario.setOnClickListener {
            val intent=Intent(this,InsertTareaActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY,Constantes.OPERACION_INSERTAR)
            startActivity(intent)

        }
    }
}
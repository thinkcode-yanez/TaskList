package com.thinkcode.tasklist

import android.content.Intent
import android.os.Bundle
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

        binding.btnAbrirFormulario.setOnClickListener {
            val intent=Intent(this,InsertTareaActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY,Constantes.OPERACION_INSERTAR)
            startActivity(intent)

        }
    }
}
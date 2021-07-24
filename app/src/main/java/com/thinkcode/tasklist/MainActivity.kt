package com.thinkcode.tasklist

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
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

    lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()
    var click=1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.start()


        binding.myMainRecycler.apply {
            layoutManager = LinearLayoutManager(applicationContext)
        }

        mainViewModel.tareasList.observe(this, Observer {
            binding.myMainRecycler.adapter = TareaAdapter(it)

        })

        mainViewModel.cargaWIN.observe(this, Observer {
            binding.myMainRecycler.adapter = TareaAdapter(mainViewModel.tareasList.value!!)
            mainViewModel.start()

        })

        binding.etBuscar.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mainViewModel.buscarTarea(p0)
                } else if (p0.toString().isEmpty()) {
                    mainViewModel.start()
                }
            }
        })

        binding.btnAbrirFormulario.setOnClickListener {
            val intent = Intent(this, InsertTareaActivity::class.java)
            intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_INSERTAR)
            startActivity(intent)

        }

        binding.btnImageOrder.setOnClickListener {

            if (click == 1) {
                val list = mainViewModel.ordenarPorFecha()
                binding.btnImageOrder.setBackgroundResource(R.drawable.ic_up)
                binding.myMainRecycler.adapter = TareaAdapter(list)
                click=2
            } else {
                val list = mainViewModel.tareasList.value
                binding.btnImageOrder.setBackgroundResource(R.drawable.ic_down)
                binding.myMainRecycler.adapter= list?.let { it1 -> TareaAdapter(it1) }
                click=1

            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_delete -> mainViewModel.deleteChecked()
            R.id.nav_priority -> mainViewModel.getByPriority()
            R.id.nav_all -> mainViewModel.start()
            R.id.nav_work -> mainViewModel.mostrarPorCategoria("Work")
            R.id.nav_home -> mainViewModel.mostrarPorCategoria("Home")
            R.id.nav_Shopping -> mainViewModel.mostrarPorCategoria("Shopping")
            R.id.nav_Payments->mainViewModel.mostrarPorCategoria("Payments")

        }
        return super.onOptionsItemSelected(item)
    }


}
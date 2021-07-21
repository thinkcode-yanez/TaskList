package com.thinkcode.tasklist.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.thinkcode.tasklist.MainActivity
import com.thinkcode.tasklist.R
import com.thinkcode.tasklist.config.Constantes
import com.thinkcode.tasklist.databinding.ItemListBinding
import com.thinkcode.tasklist.models.Tarea
import com.thinkcode.tasklist.ui.InsertTareaActivity
import com.thinkcode.tasklist.viewmodels.MainViewModel


class TareaAdapter(private val dataSet: List<Tarea>) :
    RecyclerView.Adapter<TareaAdapter.ViewHolder>() {


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        val item = dataSet?.get(position)
        viewHolder.enlazarItem(item!!)


        //item.realizado
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemListBinding.bind(view)
        var context = view.context
        val exitoso = MutableLiveData<Boolean>()
        val modelo = MainViewModel()
        val mainModelo=MainActivity()


        fun enlazarItem(item: Tarea) {

            binding.checkRealizado.isChecked = item.realizado
            // binding.etTarea.paint.isStrikeThruText=item.realizado
            if(item.realizado){
                binding.etTarea.paint.isStrikeThruText=item.realizado
                binding.etTarea.setTextColor(ContextCompat.getColor(context,R.color.design_default_color_error))
            }
            binding.etTarea.text = item.nombre
            binding.tvFecha.text = item.fecha
            binding.imPrioridad.isVisible = item.prioridad


            binding.root.setOnClickListener {
                val intent = Intent(context, InsertTareaActivity::class.java)
                intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_EDITAR)
                intent.putExtra(Constantes.ID_TAREA_KEY, item.id)
                context.startActivity(intent)
            }

            binding.checkRealizado.setOnClickListener {

                if (binding.checkRealizado.isChecked) {

                    item.realizado = true
                    binding.etTarea.paint.isStrikeThruText=item.realizado
                    exitoso.value = true
                    modelo.guardarCheck(
                        item.realizado,
                        item.nombre,
                        item.fecha,
                        item.prioridad,
                        item.id
                    )
                    binding.etTarea.setTextColor(ContextCompat.getColor(context,R.color.design_default_color_error))

                } else {
                   // binding.etTarea.paint.isStrikeThruText = true
                    item.realizado = false
                    binding.etTarea.paint.isStrikeThruText=item.realizado
                    modelo.guardarCheck(
                        item.realizado,
                        item.nombre,
                        item.fecha,
                        item.prioridad,
                        item.id
                    )
                    binding.etTarea.setTextColor(ContextCompat.getColor(context,R.color.black))
                }
            }



        }




    }
}


package com.thinkcode.tasklist.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
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

        val item = dataSet[position]
        // viewHolder.binding.checkRealizado.isChecked=item.realizado
        viewHolder.enlazarItem(item!!)

        if(item.realizado) {
            viewHolder.binding.etTarea.setTextColor( ContextCompat.getColor(
                viewHolder.context,
                R.color.design_default_color_error
            ))
            viewHolder.binding.etTarea.paint.isStrikeThruText=true
        }else{
            item.realizado=false
            viewHolder.binding.etTarea.setTextColor( ContextCompat.getColor(
                viewHolder.context,
                R.color.black
            ))
            viewHolder.binding.etTarea.paint.isStrikeThruText=false
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemListBinding.bind(view)
        var context: Context = view.context
        private val exitoso = MutableLiveData<Boolean>()
        private val modelo = MainViewModel()

        fun enlazarItem(item: Tarea) {

             binding.checkRealizado.isChecked = item.realizado
            binding.etTarea.text = item.nombre
            binding.tvFecha.text = item.fecha
            binding.imPrioridad.isVisible = item.prioridad
            //Extra Category
            binding.tvCategoria.text = item.category
            binding.root.setOnClickListener {
                val intent = Intent(context, InsertTareaActivity::class.java)
                intent.putExtra(Constantes.OPERACION_KEY, Constantes.OPERACION_EDITAR)
                intent.putExtra(Constantes.ID_TAREA_KEY, item.id)
                context.startActivity(intent)
            }


            binding.checkRealizado.setOnClickListener {

                if (binding.checkRealizado.isChecked) {

                    item.realizado = true
                    exitoso.value = true
                    modelo.guardarCheck(
                        item.realizado,
                        item.nombre,
                        item.fecha,
                        item.prioridad,
                        item.category,
                        item.id
                    )
                    binding.etTarea.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.design_default_color_error
                        )
                    )
                    binding.etTarea.paint.isStrikeThruText=true
                } else {

                    item.realizado = false
                    modelo.guardarCheck(
                        item.realizado,
                        item.nombre,
                        item.fecha,
                        item.prioridad,
                        item.category,
                        item.id
                    )
                    binding.etTarea.setTextColor(ContextCompat.getColor(context, R.color.black))
                    binding.etTarea.paint.isStrikeThruText=false

                }
            }
        }
    }

}




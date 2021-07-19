package com.thinkcode.tasklist.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.thinkcode.tasklist.R
import com.thinkcode.tasklist.config.Constantes
import com.thinkcode.tasklist.databinding.ItemListBinding
import com.thinkcode.tasklist.models.Tarea
import com.thinkcode.tasklist.ui.InsertTareaActivity


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
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var binding = ItemListBinding.bind(view)
        var context= view.context

        fun enlazarItem(item: Tarea) {

            binding.checkRealizado.isSelected=item.realizado
            binding.checkRealizado.paint.isStrikeThruText=item.realizado
            binding.etTarea.text = item.nombre
            binding.tvFecha.text =item.fecha
            binding.imPrioridad.isVisible=item.prioridad

            binding.root.setOnClickListener {
                val intent=Intent(context,InsertTareaActivity::class.java)
                intent.putExtra(Constantes.OPERACION_KEY,Constantes.OPERACION_EDITAR)
                intent.putExtra(Constantes.ID_TAREA_KEY,item.id)
                context.startActivity(intent)
            }
        }

    }
}


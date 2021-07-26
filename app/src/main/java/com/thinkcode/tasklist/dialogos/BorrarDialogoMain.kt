package com.thinkcode.tasklist.dialogos

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.thinkcode.tasklist.R


class BorrarDialogoMain(val borrarListener:BorrarListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Sure to delete selected items?")
                .setPositiveButton(
                    R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        borrarListener.borrarTarea()
                    })
                .setNegativeButton(
                    R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    interface BorrarListener{

        fun borrarTarea()
    }
}

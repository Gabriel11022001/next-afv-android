package com.example.next_vendas.utils

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.next_vendas.R

class LoaderNextVendas: DialogFragment() {

    private lateinit var txtMensagem: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogLoader = super.onCreateDialog(savedInstanceState);

        return dialogLoader
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_loader, container, false)

        this.txtMensagem = view.findViewById(R.id.txt_mensagem_loader)

        return view
    }

}
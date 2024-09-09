package com.example.next_vendas.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.next_vendas.R

class Loader(
    private val contexto: Context,
    private val container: ViewGroup
) {

    private val viewAlerta: View
    private var txtMensagem: TextView

    init {
        val layoutInflater: LayoutInflater = LayoutInflater.from(this.contexto)
        this.viewAlerta = layoutInflater.inflate(R.layout.loader_padrao, container, false)
        this.txtMensagem = viewAlerta.findViewById(R.id.txt_msg_loader)
        this.viewAlerta.visibility = View.GONE

        this.container.addView(viewAlerta)
    }

    fun iniciarLoader(mensagem: String) {
        this.txtMensagem.text = mensagem
        this.viewAlerta.visibility = View.VISIBLE
    }

    fun finalizarLoader() {
        this.viewAlerta.visibility = View.GONE
    }

}
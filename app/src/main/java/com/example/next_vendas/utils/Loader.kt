package com.example.next_vendas.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.next_vendas.R

class Loader(
    private val contexto: Context
) {

    private var txtMensagem: TextView
    private var builderLoader: AlertDialog.Builder
    private var alertaLoader: AlertDialog

    init {
        val viewLoader = LayoutInflater.from(this.contexto).inflate(R.layout.loader, null)

        this.txtMensagem = viewLoader.findViewById(R.id.txt_mensagem_loader)

        this.builderLoader = AlertDialog.Builder(this.contexto)
        this.builderLoader.setCancelable(false)
        this.builderLoader.setView(viewLoader)

        this.alertaLoader = this.builderLoader.create()
    }

    fun iniciarLoader(mensagem: String) {
        this.txtMensagem.text = mensagem
        this.alertaLoader.show()
    }

    fun finalizarLoader() {
        this.txtMensagem.text = ""
        this.alertaLoader.dismiss()
    }

}
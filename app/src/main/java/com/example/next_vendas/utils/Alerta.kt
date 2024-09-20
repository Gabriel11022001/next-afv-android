package com.example.next_vendas.utils

import android.app.AlertDialog
import android.content.Context

class Alerta(private val contexto: Context) {

    private var builderAlerta: AlertDialog.Builder
    private var alerta: AlertDialog

    init {
        this.builderAlerta = AlertDialog.Builder(this.contexto)
        this.builderAlerta.setTitle("Alerta")
        this.builderAlerta.setCancelable(false)

        this.builderAlerta.setPositiveButton("OK") { dialog, i ->
            esconder()
        }

        this.alerta = this.builderAlerta.create()
    }

    fun apresentar(mensagem: String) {
        this.alerta.setMessage(mensagem)
        this.alerta.show()
    }

    fun esconder() {
        this.alerta.dismiss()
    }

}
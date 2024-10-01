package com.example.next_vendas.utils

import android.app.AlertDialog
import android.content.Context

class AlertaConfirmarPadrao(private val contexto: Context, private val titulo: String, onConfirmar: () -> Unit) {

    private var builderAlertaConfirmarPadrao: AlertDialog.Builder
    private var alertaConfirmarPadrao: AlertDialog

    init {
        this.builderAlertaConfirmarPadrao = AlertDialog.Builder(this.contexto)
        this.builderAlertaConfirmarPadrao.setCancelable(false)
        this.builderAlertaConfirmarPadrao.setTitle(this.titulo)

        // mapear evento de confirmar
        this.builderAlertaConfirmarPadrao.setPositiveButton("Confirmo") { dialog, i ->
            onConfirmar()
        }

        this.alertaConfirmarPadrao = this.builderAlertaConfirmarPadrao.create()
    }

    fun apresentar(msg: String) {
        this.alertaConfirmarPadrao.setMessage(msg)
        this.alertaConfirmarPadrao.show()
    }

    fun esconder() {
        this.alertaConfirmarPadrao.dismiss()
    }

}
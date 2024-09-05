package com.example.next_vendas.utils

import android.app.AlertDialog
import android.content.Context
import com.example.next_vendas.listener.IOnConfirmarListener

class AlertaConfirmarVoltar(val contexto: Context, val mensagem: String, val iOnConfirmarListener: IOnConfirmarListener) {

    private var builderAlertaConfirmarRetornar: AlertDialog.Builder
    private var alertDialogConfirmarRetornar: AlertDialog

    init {
        builderAlertaConfirmarRetornar = AlertDialog.Builder(contexto)
        builderAlertaConfirmarRetornar.setCancelable(false)
        builderAlertaConfirmarRetornar.setTitle("Retornar")
        builderAlertaConfirmarRetornar.setMessage(mensagem)

        builderAlertaConfirmarRetornar.setPositiveButton("Sim") { dialog, i ->
            iOnConfirmarListener.confirmar()
        }

        builderAlertaConfirmarRetornar.setNegativeButton("Não") { dialog, i ->
            dialog.dismiss()
        }

        alertDialogConfirmarRetornar = builderAlertaConfirmarRetornar.create()
    }

    fun apresentar() {
        this.alertDialogConfirmarRetornar.show()
    }

}
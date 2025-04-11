package com.example.next_vendas.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.example.next_vendas.R

class Alerta(private val contexto: Context) {

    private var builderAlerta: AlertDialog.Builder
    private var alerta: AlertDialog
    private var viewAlerta: View
    private var txtMensagemAlerta: TextView
    private var iconeErroAlerta: ImageView
    private var btnOk: AppCompatButton

    init {
        this.builderAlerta = AlertDialog.Builder(this.contexto)
        this.builderAlerta.setCancelable(false)

        this.viewAlerta = LayoutInflater.from(this.contexto).inflate(R.layout.alerta, null)
        this.txtMensagemAlerta = this.viewAlerta.findViewById(R.id.txt_mensagem_alerta)
        this.iconeErroAlerta = this.viewAlerta.findViewById(R.id.icone_alerta_erro)
        this.btnOk = this.viewAlerta.findViewById(R.id.btn_ok)

        this.builderAlerta.setView(this.viewAlerta)

        this.btnOk.setOnClickListener { this.esconder() }

        this.alerta = this.builderAlerta.create()
    }

    fun apresentar(mensagem: String, erro: Boolean = false) {
        this.txtMensagemAlerta.text = mensagem

        if (erro) {
            this.iconeErroAlerta.visibility = View.VISIBLE
            this.btnOk.setTextColor(this.contexto.getColor(android.R.color.holo_red_dark))
            this.viewAlerta.setBackgroundColor(this.contexto.getColor(android.R.color.holo_red_dark))
        } else {
            this.iconeErroAlerta.visibility = View.GONE
            this.btnOk.setTextColor(this.contexto.getColor(android.R.color.holo_green_dark))
            this.viewAlerta.setBackgroundColor(this.contexto.getColor(android.R.color.holo_green_dark))
        }

        this.alerta.show()
    }

    fun esconder() {
        this.alerta.dismiss()
    }

}
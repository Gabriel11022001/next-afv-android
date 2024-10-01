package com.example.next_vendas.utils

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.EditText
import com.example.next_vendas.R

class GerenciaCampoObrigatorio(val contexto: Context) {

    private val campoDrawable = GradientDrawable()

    init {
        this.campoDrawable.setColor(this.contexto.getColor(R.color.white))
        this.campoDrawable.setStroke(3, this.contexto.getColor(android.R.color.holo_red_dark))
        this.campoDrawable.cornerRadius = 15f
    }

    fun setErroCampoObrigatorio(campo: EditText) {
        campo.background = this.campoDrawable
    }

    fun removeErroCampoOgrigatorio(campo: EditText) {
        this.campoDrawable.setColor(this.contexto.getColor(R.color.white))
        this.campoDrawable.setStroke(1, this.contexto.getColor(R.color.cinza_separador_linhas))
        this.campoDrawable.cornerRadius = 15f

        campo.background = this.campoDrawable
    }

}
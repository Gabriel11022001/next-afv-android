package com.example.next_vendas.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.next_vendas.utils.Constantes

open class GerenciadorBancoDados(contexto: Context): SQLiteOpenHelper(
    contexto,
    Constantes.NM_BANCO,
    null,
    Constantes.VERSAO_BANCO
) {

    protected val bancoDados: SQLiteDatabase = writableDatabase

    override fun onCreate(p0: SQLiteDatabase?) {
        this.gerarTabelas()
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    private fun gerarTabelas() {

    }

}
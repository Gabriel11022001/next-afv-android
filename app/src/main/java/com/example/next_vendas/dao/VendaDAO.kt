package com.example.next_vendas.dao

import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

class VendaDAO(contexto: Context): BaseDAO(contexto) {

    fun registrarVendaInicio(): Int {
        var idVendaInicio = 0
        val contentValues = ContentValues()
        contentValues.put("status", "andamento")
        contentValues.put("valor_total", 0.0)

        val dataVenda = Date()
        val dateTimeFormater = SimpleDateFormat("yyyy-MM-dd H:i:s")
        contentValues.put("data_venda", dateTimeFormater.format(dataVenda))

        idVendaInicio = super.bancoDados.insertOrThrow("tb_vendas", null, contentValues).toInt()

        return idVendaInicio
    }

}
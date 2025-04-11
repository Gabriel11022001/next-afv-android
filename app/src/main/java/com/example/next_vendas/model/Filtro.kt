package com.example.next_vendas.model

data class Filtro(
    var tipoFiltro: String = "",
    var where: String = "",
    var argumentosFiltro: ArrayList<String> = arrayListOf()
)
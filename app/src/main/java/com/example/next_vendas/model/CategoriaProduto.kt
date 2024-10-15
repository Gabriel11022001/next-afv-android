package com.example.next_vendas.model

data class CategoriaProduto(
    var id: Int = 0,
    var idCategoriaApi: Int = 0,
    var descricao: String = "",
    var status: Boolean = true
)
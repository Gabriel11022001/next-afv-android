package com.example.next_vendas.model

data class CategoriaProduto(
    var id: Int = 0,
    var idProdutoApi: Int = 0,
    var descricao: String = "",
    var status: Boolean = true
)
package com.example.next_vendas.model

data class ProdutoCarrinho(
    var idProdutoCarrinho: Int = 0,
    var nomeProdutoCarrinho: String = "",
    var precoUnitarioProdutoCarrinho: Double = 0.0,
    var quantidadeUnidadesProdutoCarrinho: Int = 0,
    var idVenda: Int = 0
)
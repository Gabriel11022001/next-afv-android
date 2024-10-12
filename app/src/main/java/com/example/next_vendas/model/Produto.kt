package com.example.next_vendas.model

class Produto {

    var id: Int = 0
    var nome: String = ""
    var precoVenda: Double = 0.0
    var precoCompra: Double = 0.0
    var codigo: String = ""
    var unidadesEstoque: Int = 0
    var fotoProduto: String = ""
    var status: Boolean = true
    var codigoBarras: String = ""
    var categoria: CategoriaProduto? = null

}
package com.example.next_vendas.api

interface IOnObterTotais {

    fun sincronizando()

    fun terminouObterTotais(
        totalClientes: Int = 0,
        totalCategorias: Int = 0,
        totalProdutos: Int = 0,
        totalVendas: Int = 0
    )

    fun erro(mensagem: String)

}
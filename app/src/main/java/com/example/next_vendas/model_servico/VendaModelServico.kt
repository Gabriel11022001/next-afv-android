package com.example.next_vendas.model_servico

data class VendaModelServico(
    var idVendaServidor: Int = 0,
    var dataCadastroVendaServidor: String = "",
    var valorTotalVenda: Double = 0.0,
    var status: String = "",
    var idClienteServidor: Int = 0
)
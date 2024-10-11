package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class TotalModelServico(
    @SerializedName("total_clientes")
    var totalClientes: Int = 0,
    @SerializedName("total_categorias")
    var totalCategoriasProdutos: Int = 0,
    @SerializedName("total_produtos")
    var totalProdutos: Int = 0,
    @SerializedName("total_vendas")
    var totalVendas: Int = 0
)
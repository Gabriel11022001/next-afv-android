package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class CategoriaProdutoModelServico(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("descricao")
    var descricao: String = "",
    @SerializedName("status")
    var status: Boolean = true
)
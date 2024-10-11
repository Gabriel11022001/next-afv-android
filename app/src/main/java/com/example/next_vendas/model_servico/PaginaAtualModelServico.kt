package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class PaginaAtualModelServico(
    @SerializedName("pagina_atual")
    val paginaAtual: Int = 1
)
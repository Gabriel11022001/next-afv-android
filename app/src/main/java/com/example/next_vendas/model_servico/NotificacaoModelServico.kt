package com.example.next_vendas.model_servico

import com.google.gson.annotations.SerializedName

data class NotificacaoModelServico(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("mensagem")
    var mensagem: String = "",
    @SerializedName("data_cadastro")
    var dataCadastro: String = ""
)